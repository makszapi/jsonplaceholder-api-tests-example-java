package dev.maksymzapisov.jsonplaceholder.model;

import java.util.Objects;

public class Company {

    private String name;
    private String catchPhrase;
    private String bs;

    public Company() {}

    public Company(String name, String catchPhrase, String bs) {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCatchPhrase() { return catchPhrase; }
    public void setCatchPhrase(String catchPhrase) { this.catchPhrase = catchPhrase; }

    public String getBs() { return bs; }
    public void setBs(String bs) { this.bs = bs; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) &&
                Objects.equals(catchPhrase, company.catchPhrase) &&
                Objects.equals(bs, company.bs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, catchPhrase, bs);
    }

    @Override
    public String toString() {
        return "Company{name='" + name + "', catchPhrase='" + catchPhrase + "', bs='" + bs + "'}";
    }
}
