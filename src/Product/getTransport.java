package Product;

import Utilities.DFInteraction;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.util.Vector;

    public class getTransport extends ContractNetInitiator {

        //------------------------------ Methods ------------------------------//
        public getTransport(Agent a, ACLMessage msg){
            super(a, msg);
        }

        @Override
        protected void handleInform(ACLMessage inform){
            System.out.println(myAgent.getLocalName() + ": INFORM message received");
        }

        @Override
        protected void handleAllResponses(Vector responses, Vector acceptances){

            int best_proposal = 6;
            int best_transport = -1;

            System.out.println(myAgent.getLocalName() + ": ALL PROPOSALS received");

            for (int i = 0; i<responses.size(); i++) {
                ACLMessage msg = (ACLMessage) responses.get(i);

                if (msg.getPerformative() == ACLMessage.PROPOSE) { //if their response is a proposition
                    System.out.println(msg.getSender().getLocalName() + " sent a proposition.");
                }

                int proposal_value = Integer.parseInt(msg.getContent());

                if (best_proposal == 6) {
                    best_proposal = proposal_value;
                    best_transport = i;
                }

                if (proposal_value < 6) {
                    best_transport = i;
                    best_proposal = proposal_value;
                }

            }


            for (int i = 0; i < responses.size(); i++) {
                ACLMessage msg = (ACLMessage) responses.get(i);
                ACLMessage reply = msg.createReply();

                if (i == best_transport)  // SEND ACCEPT
                    //ACLMessage.ACCEPT_PROPOSAL indicates FIPA ContractNet with a zero value
                    reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);

                else // SEND REFUSE
                    reply.setPerformative((ACLMessage.REJECT_PROPOSAL));

                acceptances.add(reply);
            }
        }


    }
//}



