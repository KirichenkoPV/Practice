package Practice_04_10_2025.behs.Utils;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DfUtils {

    public static boolean register(Agent a, String service){
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(a.getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(service);
        sd.setName(a.getLocalName());
        dfd.addServices(sd);
        try {
            DFService.register(a, dfd);
            return true;
        } catch (FIPAException e) {
            log.error("Failed to register DFAgentDescription with name {} and service {}", a.getLocalName(), service);
            e.printStackTrace();
            return false;
        }
    }

    public static List<AID> search(Agent a, String serviceName){
        List<AID> agents = new ArrayList<>();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceName);
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(a, dfd);
            for (DFAgentDescription res : result) {
                if (!res.getName().getLocalName().equals(a.getLocalName())) {
                    agents.add(res.getName());
                }
            }
            return agents;
        } catch (FIPAException e) {
            log.error("Error during searching for service {} by agent {}", serviceName, a.getLocalName(), e);
            e.printStackTrace();
            return List.of();
        }
    }

}
