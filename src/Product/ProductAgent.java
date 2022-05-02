package Product;

import Resource.ResourceAgent;
import Resource.SkillExecutionResponse;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.gui.DFAgentDscDlg;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

/**
 *
 * @author Ricardo Silva Peres <ricardo.peres@uninova.pt>
 */
public class ProductAgent extends Agent {    
    
    String id;
    ArrayList<String> executionPlan = new ArrayList<>();
    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
    DFAgentDescription reservedResource = null;
    ACLMessage msgExecuteSkill = new ACLMessage(ACLMessage.REQUEST);
    String currentSkill = "sk_q_c";



    // TO DO: Add remaining attributes required for your implementation
    
    @Override
    protected void setup() {
        Object[] args = this.getArguments();
        this.id = (String) args[0];
        this.executionPlan = this.getExecutionList((String) args[1]);


        System.out.println("Product launched: " + this.id + " Requires: " + executionPlan);
        
        // TO DO: Add necessary behaviour/s for the product to control the flow
        // of its own production

        SequentialBehaviour sb = new SequentialBehaviour();
        for(int i = 0; i < executionPlan.size(); i++) {

            sb.addSubBehaviour(new GetSkillfullAgent(this, currentSkill));
            sb.addSubBehaviour(new SkillNegotiation(this, cfp));
            sb.addSubBehaviour(new SkillExecutionRequest(this, msgExecuteSkill));
        }
        this.addBehaviour(sb);
        
    }

    @Override
    protected void takeDown() {
        super.takeDown(); //To change body of generated methods, choose Tools | Templates.
    }
    
    private ArrayList<String> getExecutionList(String productType){
        switch(productType){
            case "A": return Utilities.Constants.PROD_A;
            case "B": return Utilities.Constants.PROD_B;
            case "C": return Utilities.Constants.PROD_C;
        }
        return null;
    }
    
}
