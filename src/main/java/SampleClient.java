import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

public class SampleClient {

    public static void main(String[] theArgs) {

        // Create a FHIR client
        FhirContext fhirContext = FhirContext.forR4();
        IGenericClient client = fhirContext.newRestfulGenericClient("https://hapi.fhir.org/baseR4");
        client.registerInterceptor(new LoggingInterceptor(false));

        // Search for Patient resources
        Bundle response = client
                .search()
                .forResource("Patient")
                .sort().ascending(Patient.GIVEN)
                .returnBundle(Bundle.class)
                .execute();


        for (Bundle.BundleEntryComponent e : response.getEntry()) {
            String id = e.getResource().getId();
            Patient p = (Patient) client
                    .read()
                    .resource("Patient")
                    .withId(id)
                    .execute();

            System.out.println(" First Name: " + p.getName().get(0).getGiven()
                    + "\n Last Name: " +p.getName().get(0).getFamily()
                    + "\n Date of Birth: " + p.getBirthDate());

        }

    }

}




