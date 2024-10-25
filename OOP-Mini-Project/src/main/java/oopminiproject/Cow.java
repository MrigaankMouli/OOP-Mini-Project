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

    public double computerMarketValue() {
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

        int baseValue = 40000;
        return baseValue * valueMultiplier;
    }
}
