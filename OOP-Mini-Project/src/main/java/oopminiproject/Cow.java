package oopminiproject;

import java.util.Arrays;

//Breeds:  "Sindhi", "Tharparkar", "Gir", "Kankrej", "Hariana",
//         "Ongole", "Sahiwal", "Deoni", "Other"

public class Cow {
    private int id;
    private String breed;
    private int age;
    private int weight;
    private String insurance;
    private String vaccinationStatus;
    private String owner;

    private final String[] highValueBreeds = new String[]{"Gir", "Kankrej", "Sahiwal"};
    private final String[] midValueBreeds = new String[]{"Hariana", "Ongole", "Deoni"};
    private final String[] lowValueBreeds = new String[]{"Tharparkar", "Sindhi"};

    public Cow(int id, String breed, int age, int weight, String insurance,
               String vaccinationStatus, String owner) {
        this.id = id;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.insurance = insurance;
        this.vaccinationStatus = vaccinationStatus;
        this.owner = owner;
    }

    public int getId() { return id; }
    public String getBreed() { return breed; }
    public int getAge() { return age; }
    public int getWeight() { return weight; }
    public String getInsurance() { return insurance; }
    public String getVaccinationStatus() { return vaccinationStatus; }
    public String getOwner() { return owner; }
    public void setId(int id) { this.id = id; }
    public void setBreed(String breed) { this.breed = breed; }
    public void setAge(int age) { this.age = age; }
    public void setWeight(int weight) { this.weight = weight; }
    public void setInsurance(String insurance) { this.insurance = insurance; }
    public void setVaccinationStatus(String vaccinationStatus) { this.vaccinationStatus = vaccinationStatus; }
    public void setOwner(String owner) { this.owner = owner; }

    public int calculateMarketValue() {
        double valueMultiplier = 1.0;

        if (Arrays.stream(highValueBreeds).anyMatch(i -> i.equals(breed)))
            valueMultiplier += 0.3;
        else if (Arrays.stream(lowValueBreeds).anyMatch(i -> i.equals(breed)))
            valueMultiplier -= 0.3;

        if (age >= 2 && age <= 5) valueMultiplier += 0.3;
        else if (age <= 1 || age > 8) valueMultiplier -= 0.3;

        valueMultiplier += weight * 0.0005;

        //Vaccination status is a lil confusing: it results in *higher* market value but lower insurance premiums.
        //REMIND ME: to offset vaccination status as a risk thingy in premium calc.
        if (vaccinationStatus.equals("Full")) valueMultiplier += 0.3;
        else if (vaccinationStatus.equals("None")) valueMultiplier -= 0.3;

        final int baseMarketValue = 40000;
        double marketValue = baseMarketValue * valueMultiplier;
        return (int) marketValue;
    }

    //lowkey all these methods are placeholder calculations, but I haven't fully decided if I care or not yet
    public int calculateLRPPremium() {
        double premiumMultiplier = 1.0;

        if (age < 2 || age > 5) premiumMultiplier += 0.2;

        if (Arrays.stream(highValueBreeds).anyMatch(i -> i.equals(breed)))
            premiumMultiplier += 0.1;
        else if (Arrays.stream(lowValueBreeds).anyMatch(i -> i.equals(breed)))
            premiumMultiplier -= 0.1;

        if (vaccinationStatus.equals("Full")) premiumMultiplier -= 0.2;
        else if (vaccinationStatus.equals("None")) premiumMultiplier += 0.2;

        double basePremium = 0.04 * calculateMarketValue();
        double lrpPremium = basePremium * premiumMultiplier;
        return (int) lrpPremium;
    }

    public int calculateCIPremium() {
        double premiumMultiplier = 1.0;

        if (age >= 2 && age <= 5) premiumMultiplier -= 0.15;
        else if (age < 2 || age > 8) premiumMultiplier += 0.15;

        if (vaccinationStatus.equals("Full")) premiumMultiplier -= 0.25;
        else if (vaccinationStatus.equals("None")) premiumMultiplier += 0.25;

        premiumMultiplier += weight * 0.0001;

        double basePremium = 0.05 * calculateMarketValue();
        double  ciPremium = basePremium * premiumMultiplier;
        return (int) ciPremium;
    }

    public int calculateLGMPremium() {
        double premiumMultiplier = 1.0;

        if (vaccinationStatus.equals("Full")) premiumMultiplier -= 0.2;
        else if (vaccinationStatus.equals("None")) premiumMultiplier += 0.2;

        premiumMultiplier += weight * 0.0002;

        double basePremium = 0.06 * calculateMarketValue();
        double  lgmPremium = basePremium * premiumMultiplier;
        return (int) lgmPremium;
    }

    public int calculateYPPremium() {
        double premiumMultiplier = 1.0;

        if (age >= 2 && age <= 5) premiumMultiplier -= 0.1;
        else premiumMultiplier += 0.1;

        premiumMultiplier += weight * 0.00015;

        double basePremium = 0.045 * calculateMarketValue();
        double  ypPremium = basePremium * premiumMultiplier;
        return (int) ypPremium;
    }
}
