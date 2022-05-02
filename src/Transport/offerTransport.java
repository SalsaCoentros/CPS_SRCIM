package Transport;
import Product.GetSkillfullAgent;
import Product.getTransport;
import Resource.ResourceAgent;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;

import java.util.Random;


public class offerTransport extends ContractNetResponder {

    public offerTransport(Agent a, MessageTemplate mt){
        super(a, mt);
    }

    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException{
        //System.out.println(myAgent.getLocalName() + ": Processing CFP message");

        ACLMessage msg = cfp.createReply();

        if ( !((TransportAgent)myAgent).reserved ) { //if the resource agent is not yet reserved
            msg.setPerformative(ACLMessage.PROPOSE);
            Random rand = new Random();

            String axis_numbers = Integer.toString((int) rand.nextInt(5)+1);  // maximum 5 axis
            String payload = Integer.toString((int) rand.nextInt(11)+1);      // maximum 10 kg
            String resolution = Integer.toString((int) rand.nextDouble(6)+1); // maximum 5steps resolution
            System.out.println(myAgent.getLocalName() + " informs that it has: " + axis_numbers + "axis to do the requested transport and" +
                    "has a weight capacity of " + payload + "kg with a" + resolution + "steps of resulution");
            //String parameters = axis_numbers +"#" + payload +"#" + resolution+"#";
            msg.setContent(axis_numbers); //sends 3 random parameters to find the best option
        }
        else {
            msg.setPerformative(ACLMessage.REFUSE);
        }

            return msg;
    }

        @Override
        protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException{
            ((TransportAgent)myAgent).reserved = true;
            System.out.println(myAgent.getLocalName() + ": Preparing result of CFP");
            block(5000);
            ACLMessage msg = cfp.createReply();
            //Set a performative which is, in this case, ACLMessage.INFORM
            msg.setPerformative(ACLMessage.INFORM);
            return msg;
        }

}

