package Transport;
import Product.GetSkillfullAgent;
import Product.getTransport;
import Resource.ResourceAgent;
import Utilities.Constants;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;

import java.util.Random;
import java.util.StringTokenizer;


public class offerTransport extends ContractNetResponder {

    String init_position = null;
    String dest_position = null;
    public offerTransport(Agent a, MessageTemplate mt){
        super(a, mt);
    }

    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) {
        ACLMessage msg = cfp.createReply();

        msg.setPerformative(ACLMessage.PROPOSE);
        Random rand = new Random();

        String axis_numbers = Integer.toString(rand.nextInt(5)+1);  // maximum 5 axis
        String payload = Integer.toString(rand.nextInt(11)+1);      // maximum 10 kg
        String resolution = Integer.toString((int) rand.nextDouble(6)+1); // maximum 5steps resolution
        //System.out.println(myAgent.getLocalName() + " informs that it has: " + axis_numbers + "axis to do the requested transport and" +
                //"has a weight capacity of " + payload + "kg with a" + resolution + "steps of resulution");
        //String parameters = axis_numbers +"#" + payload +"#" + resolution+"#";
        msg.setContent(axis_numbers); //sends 3 random parameters to find the best option

        StringTokenizer content = new StringTokenizer(cfp.getContent(), Constants.TOKEN);
        init_position = content.nextToken();
        dest_position = content.nextToken();

        return msg;
    }

        @Override
        protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) {
            ACLMessage msg = cfp.createReply();
            if(init_position == dest_position) {
                //Set a performative which is, in this case, ACLMessage.INFORM
                msg.setPerformative(ACLMessage.INFORM);
            }else{
                ((TransportAgent)myAgent).reserved = true;
                System.out.println(myAgent.getLocalName() + ": Doing transportation from " + init_position + " to " + dest_position);
                //block(2000);
                ((TransportAgent)myAgent).myLib.executeMove(init_position, dest_position, propose.getSender().getLocalName());
                msg.setPerformative(ACLMessage.INFORM);
            }
            return msg;
        }

}

