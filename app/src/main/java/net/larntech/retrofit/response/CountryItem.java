package net.larntech.retrofit.response;

public class CountryItem {

    private String countryName;
    private int flagImage;
    public CountryItem(String countryName) {
        this.countryName = countryName;

    }
    public String getCountryName() {
        return countryName;
    }

}
