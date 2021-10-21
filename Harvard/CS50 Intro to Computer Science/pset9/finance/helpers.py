import os
import requests
import urllib.parse

from flask import redirect, render_template, request, session
from functools import wraps


def apology(message, code=400):
    """Render message as an apology to user."""
    def escape(s):
        """
        Escape special characters.

        https://github.com/jacebrowning/memegen#special-characters
        """
        for old, new in [("-", "--"), (" ", "-"), ("_", "__"), ("?", "~q"),
                         ("%", "~p"), ("#", "~h"), ("/", "~s"), ("\"", "''")]:
            s = s.replace(old, new)
        return s
    return render_template("apology.html", top=code, bottom=escape(message)), code


def login_required(f):
    """
    Decorate routes to require login.

    https://flask.palletsprojects.com/en/1.1.x/patterns/viewdecorators/
    """
    @wraps(f)
    def decorated_function(*args, **kwargs):
        if session.get("user_id") is None:
            return redirect("/login")
        return f(*args, **kwargs)
    return decorated_function


def lookup(symbol):
    """Look up quote for symbol."""

    # Contact API
    try:
        api_key = os.environ.get("API_KEY")
        url = f"https://cloud.iexapis.com/stable/stock/{urllib.parse.quote_plus(symbol)}/quote?token={api_key}"
        response = requests.get(url)
        response.raise_for_status()
    except requests.RequestException:
        return None

    # Parse response
    try:
        quote = response.json()
        return {
            "name": quote["companyName"],
            "price": float(quote["latestPrice"]),
            "symbol": quote["symbol"]
        }
    except (KeyError, TypeError, ValueError):
        return None


def usd(value):
    """Format value as USD."""
    return f"${value:,.2f}"


def get_stocks(db):
    purchases = db.execute("SELECT symbol, shares FROM purchases WHERE user = ?", session["user_id"])
    stocks = dict()
    for purchase in purchases:
        symbol = purchase["symbol"]
        shares = purchase["shares"]
        price = lookup(symbol)["price"]
        if symbol in stocks.keys():
            new_shares = stocks[symbol][1] + shares
            stocks[symbol] = [price, new_shares, new_shares*price]
        else:
            stocks[symbol] = [price, shares, shares*price]
    
    stocks["Total"] = 0
    
    for stock in stocks:
        if stock != "Total":
            stocks["Total"] += stocks[stock][2]
            stocks[stock][0] = usd(stocks[stock][0])
            stocks[stock][2] = usd(stocks[stock][2])
        
    user_cash = db.execute("SELECT cash FROM users WHERE id = ?", session["user_id"])[0]["cash"]
    stocks["Cash"] = usd(user_cash)
    stocks["Total"] += user_cash
    stocks["Total"] = usd(stocks["Total"])
    
    return stocks