import java.util.Scanner;

public class VaccinationSystem {

    static Scanner scanner = new Scanner(System.in);
    static PatientDetails[] pd = new PatientDetails[100];
    static int totalVaccineStock = 1000;  // Total stock of vaccines for the day
    static int remainingVaccineStock = totalVaccineStock;  // Remaining stock after each registration

    public static void main(String[] args) {
        int choice;

        while (true) {
            System.out.println("\nVaccination System Menu:");
            System.out.println("1. Verify Patient");
            System.out.println("2. Register New Patient");
            System.out.println("3. View Vaccinated Patients List");
            System.out.println("4. View Remaining Vaccine Stock");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    verifyPatient();
                    break;
                case 2:
                    registerNewPatient();
                    break;
                case 3:
                    viewVaccinatedPatientsList();
                    break;
                case 4:
                    viewRemainingVaccineStock();
                    break;
                case 5:
                    System.out.println("Exiting the Vaccination System. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    static void verifyPatient() {
        System.out.println("\nVerify Patient:");
        System.out.print("Enter Aadhar number: ");
        long aadhar = scanner.nextLong();

        PatientDetails patient = findPatient(aadhar);

        if (patient != null) {
            System.out.println("Patient Details:");
            System.out.println("Aadhar: " + patient.aadhar);
            System.out.println("Vaccination Status: " + (patient.vaccineDoses > 0 ? "Vaccinated" : "Not Vaccinated"));
        } else {
            System.out.println("Patient not found. Please check the Aadhar number and try again.");
        }
    }

    static void registerNewPatient() {
        System.out.println("\nRegister New Patient:");

        if (remainingVaccineStock <= 0) {
            System.out.println("Sorry, no vaccines available for registration. Try again tomorrow.");
            return;
        }

        PatientDetails newPatient = new PatientDetails();

        System.out.print("Enter Aadhar number: ");
        newPatient.aadhar = scanner.nextLong();

        // Check if patient already exists
        if (findPatient(newPatient.aadhar) != null) {
            System.out.println("Patient already registered. Cannot register again.");
            return;
        }

        System.out.print("Enter age: ");
        newPatient.age = scanner.nextInt();

        System.out.print("Enter vaccine type (1: Covishield, 2: Covaxin): ");
        newPatient.vaccineType = scanner.nextInt();

        // Check if the entered vaccine type is valid
        if (newPatient.vaccineType != 1 && newPatient.vaccineType != 2) {
            System.out.println("Invalid vaccine type. Please enter 1 or 2.");
            return;
        }

        // Update vaccine stock and patient details
        remainingVaccineStock--;
        newPatient.vaccineDoses++;

        // Store patient details in the array
        for (int i = 0; i < pd.length; i++) {
            if (pd[i] == null) {
                pd[i] = newPatient;
                break;
            }
        }

        System.out.println("Patient registered successfully.");
    }

    static void viewVaccinatedPatientsList() {
        System.out.println("\nVaccinated Patients List:");
        for (PatientDetails patient : pd) {
            if (patient != null && patient.vaccineDoses > 0) {
                System.out.println("Aadhar: " + patient.aadhar);
                System.out.println("Age: " + patient.age);
                System.out.println("Vaccine Type: " + patient.vaccineType);
                System.out.println("Vaccination Status: Vaccinated");
                System.out.println("---------------");
            }
        }
    }

    static void viewRemainingVaccineStock() {
        System.out.println("\nRemaining Vaccine Stock: " + remainingVaccineStock);
    }

    static PatientDetails findPatient(long aadhar) {
        for (PatientDetails patient : pd) {
            if (patient != null && patient.aadhar == aadhar) {
                return patient;
            }
        }
        return null;
    }

    static class PatientDetails {
        long aadhar;
        int age;
        int vaccineType;
        int vaccineDoses;

        public PatientDetails() {
            this.vaccineDoses = 0;
        }
    }
}
