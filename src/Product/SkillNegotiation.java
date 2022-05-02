package Product;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.util.Enumeration;
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

        int best_proposal = 0;
        int best_resource = -1;
        System.out.println(myAgent.getLocalName() + ": ALL PROPOSALS received");

        for (int i = 0; i<response.size(); i++) {
             ACLMessage msg = (ACLMessage)response.get(i);

             if (msg.getPerformative() == ACLMessage.PROPOSE) { //if their response is a proposition
                 System.out.println(msg.getSender().getLocalName() + " sent a proposition.");

                 int proposal_value = Integer.parseInt(msg.getContent());

                 if (best_proposal == 0) {
                     best_proposal = proposal_value;
                     best_resource = i;
                 }

                 if (proposal_value < best_proposal) {
                     best_resource = i;
                     best_proposal = proposal_value;
                 }

             }
        }

        if (best_resource != -1) {

            for (int i = 0; i < response.size(); i++) {
                ACLMessage msg = (ACLMessage) response.get(i);
                ACLMessage reply = msg.createReply();

                if (i == best_resource)  // SEND ACCEPT
                    reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);

                else // SEND REFUSE
                    reply.setPerformative((ACLMessage.REJECT_PROPOSAL));

                acceptances.add(reply);
            }

            String agentName = ((ACLMessage)response.get(best_resource)).getSender().getLocalName();
            ((ProductAgent)myAgent).reservedAgent = new AID(agentName,false);
        }



        /*
        ACLMessage auxMsg = (ACLMessage)response.get(0);
        System.out.println(((ACLMessage) response.get(0)).getSender().getLocalName());
        ACLMessage reply = auxMsg.createReply();
        reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        acceptances.add(reply);

         */
    }

}
