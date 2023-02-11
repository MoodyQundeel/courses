from django import forms


class newListingForm(forms.Form):
    title = forms.CharField(max_length=64,
                            label=False,
                            required=True, widget=forms.TextInput(
                                attrs={'class': "form-control mb-3", 'placeholder': "Title"}))
    description = forms.CharField(max_length=500,
                                  label=False, required=True, widget=forms.Textarea(attrs={'class': "form-control mb-3", 'placeholder': "Description"}))
    category = forms.CharField(max_length=64,
                               label=False,
                               required=False, widget=forms.TextInput(
                                   attrs={'class': "form-control mb-3", 'placeholder': "Category (optional)"}))
    photo = forms.URLField(
        required=False,
        label=False, widget=forms.URLInput(attrs={'class': "form-control mb-3", 'placeholder': "Image URL (optional)"}))
    starting_bid = forms.IntegerField(required=True,
                                      label=False,
                                      widget=forms.NumberInput(attrs={'class': "form-control", 'placeholder': "Starting bid"}))


class placeBidForm(forms.Form):
    bid = forms.IntegerField(label=False, widget=forms.NumberInput(
        attrs={'class': "form-control mb-3", 'placeholder': "Bid"}))


class commentForm(forms.Form):
    text = forms.CharField(max_length=500,
                           label=False, required=True, widget=forms.Textarea(attrs={'class': "form-control mb-3", 'placeholder': "Comment", 'rows': "2"}))
