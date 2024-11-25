package pl.infoshare.clinicweb.file;

import io.github.viepovsky.polishutils.pesel.Pesel;
import io.github.viepovsky.polishutils.pesel.PeselGenerator;
import net.datafaker.Faker;
import pl.infoshare.clinicweb.doctor.Specialization;
import pl.infoshare.clinicweb.patient.Address;
import pl.infoshare.clinicweb.patient.Patient;
import pl.infoshare.clinicweb.user.entity.Gender;
import pl.infoshare.clinicweb.user.entity.PersonDetails;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GeneratorData {


    private static final int count = 15;


    static Specialization[] specializations = Specialization.values();
    static List<Specialization> list = Arrays.stream(specializations).toList();
    static Random rand = new Random();

    public static PersonDetails generatePersonDetails() {
        Faker faker = new Faker(new Locale("pl"));
        PersonDetails personDetails = new PersonDetails();
        String generatedPesel = PeselGenerator.generatePeselStatic();
        Pesel pesel = new Pesel(generatedPesel);
        personDetails.setPesel(generatedPesel);
        personDetails.setName(generateFirstNameForPesel(generatedPesel, faker));
        personDetails.setSurname(faker.name().lastName());
        personDetails.setPhoneNumber(faker.phoneNumber().phoneNumber());
        personDetails.setBirthDate(pesel.getBirthDate());
        personDetails.setGender(Gender.valueOf(pesel.getGender()));
        return personDetails;
    }

    private static Address generateAddress() {
        Faker faker = new Faker(new Locale("pl"));
        Address address = new Address();
        address.setCity(faker.address().city());
        address.setCountry(faker.address().country());
        address.setZipCode(faker.address().zipCode());
        address.setStreet(faker.address().streetAddress());
        address.setHouseNumber(faker.address().buildingNumber());
        address.setFlatNumber(faker.address().streetAddressNumber());
        return address;
    }

    private static boolean isFemalePesel(String pesel) {
        int genderDigit = Character.getNumericValue(pesel.charAt(9));
        return genderDigit % 2 == 0;
    }

    private static String generateFirstNameForPesel(String pesel, Faker faker) {
        if (isFemalePesel(pesel)) {
            return faker.name().femaleFirstName();
        } else {
            return faker.name().malefirstName();
        }
    }


    private static Patient generateRandomPatient() {
        Patient patient = new Patient();
        patient.setPersonDetails(generatePersonDetails());
        patient.setAddress(generateAddress());
        return patient;
    }
}
