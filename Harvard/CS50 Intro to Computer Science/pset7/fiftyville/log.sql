-- Keep a log of any SQL queries you execute as you solve the mystery.
-- 7, 28, 2020
-- Chamberlin Street

-- get first lead
SELECT description FROM crime_scene_reports
WHERE day = 28 AND month = 7
AND street = "Chamberlin Street";
-- 10:15am, courthouse, interviews?

-- to know what the interviews contained
SELECT name, transcript FROM interviews
WHERE year = 2020 AND month = 7
AND day = 28;
-- left courthouse within 10 minutes of the theft in car
-- withdraw money from ATM on FiferStreet
-- talked on phone for less a minute
-- took earliest flight out from fiftyville on 29
-- accomplice bought the ticket

-- check cars leaving the courthouse within 10 minutes of the theft
SELECT license_plate FROM courthouse_security_logs
WHERE year = 2020 AND month = 7
AND day = 28 AND hour = 10
AND minute >= 15 AND minute <= 25
AND activity = "exit";
-- suspected licensce plates:
-- 5P2BI95
-- 94KL13X
-- 6P58WS2
-- 4328GD8
-- G412CB7
-- L93JTIZ
-- 322W7JE
-- 0NTHK55

-- check withdrawals from atms on fiferStreet on that day 
SELECT account_number, amount FROM atm_transactions
WHERE year = 2020 AND month = 7
AND day = 28 AND atm_location LIKE "fifer street"
AND transaction_type LIKE "withdraw";
-- suspected account numbers
-- 28500762 | 48
-- 28296815 | 20
-- 76054385 | 60
-- 49610011 | 50
-- 16153065 | 80
-- 25506511 | 20
-- 81061156 | 30
-- 26013199 | 35

-- check phonecalls lasting less than a minute on that day
SELECT caller, receiver FROM phone_calls
WHERE year = 2020 AND month = 7
AND day = 28 AND duration < 60;
-- suspected phone numbers
-- (130) 555-0289 | (996) 555-8899
-- (499) 555-9472 | (892) 555-8872
-- (367) 555-5533 | (375) 555-8161
-- (499) 555-9472 | (717) 555-1342
-- (286) 555-6063 | (676) 555-6554
-- (770) 555-1861 | (725) 555-3243
-- (031) 555-6622 | (910) 555-3251
-- (826) 555-1652 | (066) 555-9701
-- (338) 555-6650 | (704) 555-2131

-- check airports at fiftyville
SELECT full_name, abbreviation FROM airports
WHERE city LIKE "fiftyville";
-- the only airport at fiftyville : Fiftyville Regional Airport | CSF

-- check the earliest flight leaving CSF on the 29th
SELECT hour, minute, destination_airport_id, flights.id FROM flights
JOIN airports ON flights.origin_airport_id = airports.id
WHERE day = 29 AND year = 2020 AND month = 7
AND abbreviation = "CSF" ORDER BY hour DESC, minute DESC;
-- earliest flight at 8 20, destination_airport_id = 4

-- check destination airport name, city
SELECT full_name, city FROM airports
WHERE id = 4;
-- they are heading to London! (Heathrow Airport)

-- identify suspects ids based on suspected bank accounts
SELECT name, id, passport_number, creation_year FROM people
JOIN bank_accounts ON people.id = bank_accounts.person_id
WHERE account_number IN (SELECT account_number FROM atm_transactions
WHERE year = 2020 AND month = 7
AND day = 28 AND atm_location LIKE "fifer street"
AND transaction_type LIKE "withdraw");
-- current suspects and their ids
-- Ernest | 686048 | 5773159633 | 2010
-- Russell | 514354 | 3592750733 | 2012
-- Roy | 458378 | 4408372428 | 2012
-- Bobby | 395717 | 9878712108 | 2014
-- Elizabeth | 396669 | 7049073643 | 2014
-- Danielle | 467400 | 8496433585 | 2014
-- Madison | 449774 | 1988161715 | 2015
-- Victoria | 438727 | 9586786673 | 2018

-- check which of the subjects where on the flight
SELECT name, id, phone_number, license_plate FROM people
WHERE name IN (SELECT name FROM people
JOIN bank_accounts ON people.id = bank_accounts.person_id
WHERE account_number IN (SELECT account_number FROM atm_transactions
WHERE year = 2020 AND month = 7
AND day = 28 AND atm_location LIKE "fifer street"
AND transaction_type LIKE "withdraw"))
AND passport_number IN (SELECT passport_number FROM passengers
JOIN flights ON passengers.flight_id = flights.id
WHERE flights.id = 36);
-- suspects on flight:
-- Bobby | 395717 | (826) 555-1652 | 30G67EN
-- Madison | 449774 | (286) 555-6063 | 1106N58
-- Danielle | 467400 | (389) 555-5198 | 4328GD8
-- Ernest | 686048 | (367) 555-5533 | 94KL13X

-- check which of the suspects license plate matches the ones exiting the courthouse
SELECT name, id, phone_number, license_plate FROM people
WHERE name IN
(SELECT name FROM people
WHERE name IN (SELECT name FROM people
JOIN bank_accounts ON people.id = bank_accounts.person_id
WHERE account_number IN (SELECT account_number FROM atm_transactions
WHERE year = 2020 AND month = 7
AND day = 28 AND atm_location LIKE "fifer street"
AND transaction_type LIKE "withdraw"))
AND passport_number IN (SELECT passport_number FROM passengers
JOIN flights ON passengers.flight_id = flights.id
WHERE flights.id = 36))
AND license_plate IN (SELECT license_plate FROM courthouse_security_logs
WHERE year = 2020 AND month = 7
AND day = 28 AND hour = 10
AND minute >= 15 AND minute <= 25
AND activity = "exit");
-- suspects with matching license plate:
-- Danielle | 467400 | (389) 555-5198 | 4328GD8
-- Ernest | 686048 | (367) 555-5533 | 94KL13X

-- filter suspects based on phone_numbers
SELECT name, id, phone_number FROM people WHERE
name IN
(SELECT name FROM people
WHERE name IN
(SELECT name FROM people
WHERE name IN (SELECT name FROM people
JOIN bank_accounts ON people.id = bank_accounts.person_id
WHERE account_number IN (SELECT account_number FROM atm_transactions
WHERE year = 2020 AND month = 7
AND day = 28 AND atm_location LIKE "fifer street"
AND transaction_type LIKE "withdraw"))
AND passport_number IN (SELECT passport_number FROM passengers
JOIN flights ON passengers.flight_id = flights.id
WHERE flights.id = 36))
AND license_plate IN (SELECT license_plate FROM courthouse_security_logs
WHERE year = 2020 AND month = 7
AND day = 28 AND hour = 10
AND minute >= 15 AND minute <= 25
AND activity = "exit"))
AND phone_number IN (SELECT caller FROM phone_calls
WHERE year = 2020 AND month = 7
AND day = 28 AND duration < 60);
-- IT IS ERNEST! AND THE ACCOMPLICE MUST BE...
SELECT name FROM people
WHERE phone_number = "(375) 555-8161";
-- BERTHOLD!