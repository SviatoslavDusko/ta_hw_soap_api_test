package entity.nested;

import java.util.Objects;

public class Birth {
    private String date;
    private String country;
    private String city;

    public Birth() {
    }

    public Birth(String date, String country, String city) {
        this.date = date;
        this.country = country;
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Birth birth = (Birth) o;
        return Objects.equals(date, birth.date) &&
                Objects.equals(country, birth.country) &&
                Objects.equals(city, birth.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, country, city);
    }

    @Override
    public String toString() {
        return "Birth{" +
                "date='" + date + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
