package Practice_04_10_2025.behs;

import Practice_04_10_2025.behs.Utils.BidResponse;
import Practice_04_10_2025.behs.Utils.JsonUtils;
import Practice_04_10_2025.behs.Utils.PriceProposal;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

@Slf4j
public class Bayer extends Behaviour {
    private MessageTemplate mt;

    @Override
    public void onStart(){
        mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
        log.info("Покупатель {} начал работу", myAgent.getLocalName());
    }

    @Override
    public void action(){
        ACLMessage m = myAgent.receive(mt);
        if (m != null) {
            // Декодируем JSON и получаем Optional<PriceProposal>
            Optional<PriceProposal> optionalProposal = JsonUtils.fromJson(m.getContent(), PriceProposal.class);

            // Проверяем, есть ли значение в Optional
            if (optionalProposal.isPresent()) {
                PriceProposal proposal = optionalProposal.get(); // Извлекаем объект
                log.info("Покупатель {} получил предложение со стартовой ценой: {}",
                        myAgent.getLocalName(), proposal.getStartPrice());

                // Генерируем свою ставку
                double minPrice = proposal.getStartPrice();
                double maxPrice = minPrice * 3;
                double myBid = minPrice + Math.random() * (maxPrice - minPrice);

                // Отправляем ответ
                ACLMessage answer = new ACLMessage(ACLMessage.PROPOSE);
                answer.addReceiver(m.getSender());
                answer.setContent(JsonUtils.toJson(new BidResponse(myBid)));
                myAgent.send(answer);
                log.info("Покупатель {} отправил ставку: {}", myAgent.getLocalName(), myBid);
            } else {
                log.error("Не удалось декодировать предложение от продавца");
            }
        } else {
            block();
        }
    }

    @Override
    public boolean done(){
        return false;
    }
}