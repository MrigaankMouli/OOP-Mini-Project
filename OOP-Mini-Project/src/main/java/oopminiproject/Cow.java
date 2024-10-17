package oopminiproject;

public class Cow {
    private int id;
    private String breed;
    private int age;
    private int weight;
    private String insurance;
    private String vaccinationStatus;
    private String owner;

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

    public int getId() {
        return id;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getVaccinationStatus() {
        return vaccinationStatus;
    }

    public String getOwner() {
        return owner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public void setVaccinationStatus(String vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
