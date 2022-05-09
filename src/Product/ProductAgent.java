package Product;


import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
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
    String currentSkill = null;
    String currentLocation = "Source"; //this has to be changed, it limits the number of sources
    String nextLocation = null;
    boolean skillReserved = false;
    boolean transportDone = false;
    boolean skillDone = false;
    String objectWeight = Integer.toString(6);



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
        for (String s : executionPlan) {
            sb.addSubBehaviour(new newExecPlanStep(this, s));
            sb.addSubBehaviour(new GetSkillfullAgent(this));
            sb.addSubBehaviour(new SkillNegotiation(this, cfp));
            //sb.addSubBehaviour(new GetSkillfullAgent(this));
            sb.addSubBehaviour(new checkTransportation());
            //sb.addSubBehaviour(new getTransport(this, cfp));
            //sb.addSubBehaviour(new SkillExecutionRequest(this, msgExecuteSkill));
            sb.addSubBehaviour(new SkillExecution());
        }
        sb.addSubBehaviour(new DestroyProduct());
        this.addBehaviour(sb);
        
    }

    @Override
    protected void takeDown() {
        super.takeDown(); //To change body of generated methods, choose Tools | Templates.
    }
    
    private ArrayList<String> getExecutionList(String productType){
        return switch (productType) {
            case "A" -> Utilities.Constants.PROD_A;
            case "B" -> Utilities.Constants.PROD_B;
            case "C" -> Utilities.Constants.PROD_C;
            default -> null;
        };
    }
    
}
