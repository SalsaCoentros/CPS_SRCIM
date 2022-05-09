package Resource;

import Utilities.Constants;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;

import java.util.Random;

public class OfferSkill extends ContractNetResponder {

    public OfferSkill(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleCfp (ACLMessage cfp) {
        //System.out.println(myAgent.getLocalName() + ": Processing CFP message");

        ACLMessage msg = cfp.createReply();

        if ( !((ResourceAgent)myAgent).reserved ) { //if the resource agent is not yet reserved

            ((ResourceAgent)myAgent).reservedSkill = cfp.getContent();

            msg.setPerformative(ACLMessage.PROPOSE);
            Random rand = new Random();

            String infoSkills = "";
            for (String s : ((ResourceAgent)myAgent).associatedSkills ) {
                if (!infoSkills.equals(""))
                    infoSkills = infoSkills + Constants.TOKEN;
                infoSkills = infoSkills + s;
            }

            System.out.println(infoSkills);


            String timeProduction = Integer.toString(rand.nextInt(100) + 1);
            //System.out.println(myAgent.getLocalName() + " informs that it takes: " + timeProduction + " to do the requested skill");
            msg.setContent(timeProduction + Constants.TOKEN + ((ResourceAgent)myAgent).location + Constants.TOKEN + infoSkills); //sends a random value (between 1 and 100) considered as the time (in sec's) it takes to do a certain skill
        }
        else {
            msg.setPerformative(ACLMessage.REFUSE);
        }

        return msg;
    }

    @Override
    protected ACLMessage handleAcceptProposal (ACLMessage cfp, ACLMessage propose, ACLMessage accept) {
        ((ResourceAgent)myAgent).reserved = true;
        ACLMessage msg = cfp.createReply();
        msg.setPerformative(ACLMessage.INFORM);
        return msg;
    }

}
