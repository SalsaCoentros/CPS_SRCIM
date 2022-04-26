package Product;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.util.Vector;

public class SkillNegotiation extends ContractNetInitiator {

    public SkillNegotiation(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        System.out.println(myAgent.getLocalName() + ": INFORM message received");
    }

    @Override
    protected void handleAllResponses (Vector response, Vector acceptances) {
        System.out.println(myAgent.getLocalName() + ": ALL PROPOSALS received");
        ACLMessage auxMsg = (ACLMessage)response.get(0);
        ACLMessage reply = auxMsg.createReply();
        reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        acceptances.add(reply);
    }
}
