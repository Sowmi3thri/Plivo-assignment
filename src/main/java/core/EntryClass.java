package core;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by sowmithri.ravi on 10/7/16.
 */
public class EntryClass {


    /**
     * This is the entry method to the complete scenario flow
     * args[0] = auth_id
     * args[1] = auth_token
     * @param args
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        Orchestrator orchestrator = new Orchestrator();
        Scanner sc = new Scanner(System.in);
        if(args.length!=2) {
            System.out.println("There should be exactly two arguments");
            System.exit(1);
        }
        String auth_id = args[0];
        String auth_token = args[1];
        PlivoAuthenticator plivoAuthenticator = PlivoAuthenticator.getInstance(auth_id, auth_token);
        System.out.println("Authenticating user..");
        if(!plivoAuthenticator.isAuthenticUser()) {
            System.out.println("Not an authentic user. Check your credentials");
            System.exit(1);
        }

        System.out.println("Getting initial cash credit before the scenario..");
        float initial_cash_credit = orchestrator.getCashCredit();
        System.out.println("Initial cash credit : " + initial_cash_credit);


        float spent_rate = 0;

        System.out.println("Enter country_iso to search");
        String country_iso = sc.next();
        System.out.println("Enter pattern to search");
        String pattern = sc.next();

        System.out.println("Searching for numbers with country_iso : " + country_iso + " and pattern : " + pattern);
        List<String> phone_numbers = orchestrator.searchNumber(country_iso, pattern);
        System.out.println("Phone numbers are : " + phone_numbers);

        System.out.println("Getting pricing for the country..");
        float pricing = orchestrator.getPricing(country_iso);
        System.out.println("Pricing for " + country_iso + " : " + pricing);


        for(String phone_number : phone_numbers) {
            System.out.println("Buying number : " + phone_number);
            orchestrator.buyNumber(phone_number);

            System.out.println("Enter destination phone number");
            String dest_ph = sc.next();
            System.out.println("Enter text to be sent");
            String message = sc.next();
            System.out.println("Sending message from " + phone_number + " to " + dest_ph);
            String uuid = orchestrator.sendMessage(phone_number,dest_ph,message);
            System.out.println("Message uuid : " + uuid);

            System.out.println("Printing the message details for uuid " + uuid);
            orchestrator.printMessageDetails(uuid);

            System.out.println("Getting message outbound rate..");
            float message_rate = orchestrator.getMessageRate(uuid);
            System.out.println("Message rate : " + message_rate);
            if(message_rate!=pricing) {
                System.out.println("Pricing and rate do not match");
            } else {
                System.out.println("Pricing and rate match");
            }
            spent_rate += message_rate;
        }

        float final_cash_credit = orchestrator.getCashCredit();
        System.out.println("Initial cash credit : " + initial_cash_credit);
        System.out.println("Total spent : " + spent_rate);
        System.out.println("Final cash credit : " + final_cash_credit);
    }
}
