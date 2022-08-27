package org.bassani.examplemodellib.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum MessageEnum {

    JSON_MAPPING_ERROR_JSONCONVERTER("Falha para converter em Json!",  "Erro genérico! JSONConverter.class", HttpStatus.INTERNAL_SERVER_ERROR),
    JSON_MAPPING_ERROR_ABSTRACTCONSUMER("Falha no mapeamento da mensagem recebida no rabbit",  "Erro genérico! AbstractConsumer.class", HttpStatus.BAD_REQUEST),
    SIMULATION_FACADE_BRANCHES_NOT_FOUND("Falha na busca das lojas para simulação", "Lojas não encontradas", HttpStatus.NOT_FOUND),
    SIMULATION_FACADE_GET_ALL_BRANCHES_NOT_FOUND("Falha na busca do total da simulação", "Total de produtos não encontrado para estes produtos", HttpStatus.NOT_FOUND),
    SIMULATION_FACADE_PRODUCT_DATA_NOT_FOUND("Falha na busca do dados do produto para simulação", "Produtos não encontrados", HttpStatus.NOT_FOUND),
    SIMULATION_FACADE_PRODUCT_BASE_VALUE_NOT_FOUND("Falha na busca do dados do valor base dos produtos para simulação", "Valor base dos Produtos não encontrados", HttpStatus.NOT_FOUND),
    SIMULATION_CALCULATION_ORDERED_QUANTITY_BAD_REQUEST("Não foi possivel calcular a quantidade pedida!", " Verifique" +
            " os valores do calculo!",
            HttpStatus.BAD_REQUEST),
    SIMULATION_CALCULATION_PRODUCT_ID_NOT_FOUND("Produto não encontrado!", " Verifique o ID!", HttpStatus.NOT_FOUND),
    SIMULATION_CALCULATION_SALES_DAY_NOT_FOUND("Dias de Compra não encontrado!", " Verifique a mensagem consumida no " +
            "Rabbit!",
            HttpStatus.NOT_FOUND),
    WEEKLY_FORECAST_SERVICE_UNAVALIABLE("Instancia do microserviço WeeklyForecast está fora!", " É preciso reiniciar " +
            "o pod",
            HttpStatus.SERVICE_UNAVAILABLE),
    SIMULATION_CALCULATION_METHOD_CALCULATION_BASIS(" Erro com parametro de Base de calculo durante o consumo da " +
            "mensagem", " Base de calculo não pode vir nulo! ", HttpStatus.BAD_REQUEST),
    PAYMENT_TERM_ID_NULL(" ID de condição campo obrigatório! ", " ID não pode vir nulo! ",
            HttpStatus.BAD_REQUEST),
    SIMULATION_CALCULATION_STOCK_COVERAGE_CURRENT_BAD_REQUEST("Não foi possivel calcular a cobertura de estoque atual ",
            " " +
                    "Verifique" +
                    " os valores do calculo!",
            HttpStatus.BAD_REQUEST),
    PROFILE_NOT_FOUND_EXCEPTION("Perfil não encontrado!", "Não foi possível localizar esse Perfil! A descrição " +
            "informada não foi encontrada no banco de dados.", HttpStatus.NOT_FOUND),
    GROUP_NOT_FOUND_EXCEPTION("Grupo não encontrado!", "Não foi possível localizar o Grupo! É preciso vincular " +
            "usuário a um grupo no KeyCloak!", HttpStatus.NOT_FOUND),
    SIMULATION_APPROVAL_COMPETENCY_NULL_SIMULATION_ID_EXCEPTION("Campo está vazio ou null!", "Não foi " +
            "possível " +
            "encontrar ID da Simulação!", HttpStatus.BAD_REQUEST),
    SIMULATION_APPROVAL_COMPETENCY_NULL_PROFILE_ID_EXCEPTION("Campo está vazio ou null!", "Não foi possível " +
            "encontrar ID do Perfil (cargo)!", HttpStatus.BAD_REQUEST),
    SIMULATION_APPROVAL_COMPETENCY_ALREADY_EXISTS_EXCEPTION("Aprovador Final já está salvo!", "Não foi possível " +
                                                                    "salvar aprovador, já há um aprovador final! [%s]",
            HttpStatus.BAD_REQUEST),
    NULL_PARAMETER_EXCEPTION("Parâmetro não pode ser nulo!",  "Não é permitido campo de parâmetro da requisição nulo.",
            HttpStatus.BAD_REQUEST),
    NOTIFY_JSON_MAPPING_ERROR_NOT_FOUND("Falha ao converter campo mensagem", "Campo DOC_MENSAGEM inválido", HttpStatus.NOT_FOUND),
    SIMULATION_APPROVAL_FLOW_NOT_FOUND_EXCEPTION("Status não encontrado!",  "Não foi possível encontrar status para o" +
            " fluxo de aprovação [Valor Informado: %d]",
            HttpStatus.NOT_FOUND),
    AREA_NOT_FOUND_EXCEPTION("Area não encontrada!",  "Não foi possível encontrar a area informada [Valor Informado: %d]",
            HttpStatus.NOT_FOUND),
    SIMULATION_STATUS_NOT_FOUND_EXCEPTION("Status da simulação não encontrado!",  "Não foi possível encontrar o " +
            "status da simulação informado [Valor Informado: %d]",
            HttpStatus.NOT_FOUND),
    REPROVAL_REASON_NULL_EXCEPTION("Motivo não foi preenchido!",  "Não é permitido reprovar uma simulação sem motivo",
            HttpStatus.BAD_REQUEST),
    NOTIFICATION_MESSAGE_NOT_FOUND_EXCEPTION("Mensagem não encontrada!",  "Não foi possível localizar essa Mensagem! O id informado não foi " +
            "encontrado no banco de dados. [ID Mensagem: %d]",
            HttpStatus.NOT_FOUND),

    WEEKLY_FORECAST_GATEWAY_TIMEOUT("Instancia do microserviço WeeklyForecast não respondeu no tempo necessário!", " " +
            "É preciso " +
            "verificar o banco de dados do WeeklyForecast.",
            HttpStatus.GATEWAY_TIMEOUT),

    PARAMETER_GATEWAY_TIMEOUT("Instancia do microserviço Parameter não respondeu no tempo necessário!", " " +
            "É preciso verificar o banco de dados do Parameter.",
            HttpStatus.GATEWAY_TIMEOUT),

    PURCHASE_VALUE_NULL("amount é um campo obrigatório! ", "amount é obrigatório quando o tipo de " +
            "simulação for por 'valor da compra'! ",
            HttpStatus.BAD_REQUEST),

    CATEGORIES_VALUE_NULL("categoria é um campo obrigatório! ", "Pelo menos uma categoria deverá ser " +
            "informada, quando o tipo de simulação for por 'valor da compra'! ",
            HttpStatus.BAD_REQUEST),

    SIMULATION_PENDING_APPROVAL_DATE_BAD_REQUEST("Intervalo dos campos de datas de envio para " +
            "aprovação maior que o período de 7 dias.", "Verifique os valores das datas preenchidas.",
            HttpStatus.BAD_REQUEST),

    USER_ALREADY_EXISTS("O usuário de mesmo e-mail já existe", "Foi encontrado um usuário de mesmo e-mail cadastrado." +
            " Por favor, utilize a edição caso queira alterar o usuário já cadastrado ou insira um e-mail" +
            " diferente na edição.",
            HttpStatus.CONFLICT),
    USER_EMAIL_NULL("Campo e-mail do usuário é obrigatório!", "Para cadastro ou edição de usuários, é necessário " +
            "preenchimento de e-mail.",
            HttpStatus.BAD_REQUEST),
    USER_ID_KEYCLOAK_NULL("Campo id do usuário no KeyCloak é obrigatório!", "Para a edição do usuário, é necessário " +
            "preenchimento do ID do usuário no KeyCloak.", HttpStatus.BAD_REQUEST),
    USER_NAME_LETTERS_ONLY("Nome e sobrenome do usuário devem contem apenas letras!", "Por favor, verificar o " +
            "preenchimento dos campos de nome e sobrenome.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("Usuário não encontrado!", "Usuário criado ou usado para edição não foi encontrado.",
            HttpStatus.NOT_FOUND),
    USER_POSITION_ALREADY_EXISTS("O usuário da edição possui mais de um cargo!", "Por favor, verificar no KeyCloak a " +
            "atribuição de cargos/grupos desse usuário.", HttpStatus.CONFLICT),
    ORDERS_NUMBER_NOT_FOUND("O(s) número(s) de pedido(s) a seguir não foram encontrados: %s","Pedido(s) não encontrado(s)",
            HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND("Pedidos da simulação de id %s não encontrados",  "Não foram encontrados na base de dados os " +
            "pedidos da simulação.", HttpStatus.NOT_FOUND),
    INVALID_STATUS_SEND_ORDERS("Status da simulação invalido para envio de pedidos",
            "A simulação %s precisa estar no status 'Emissão de Pedido Finalizado' para envio de pedidos.",
            HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR_SEND_ORDERS("Erro no envio de pedidos",
            "Houve um erro na comunicação com o MS Order para o envio de pedidos da simulação {0}.",
            HttpStatus.INTERNAL_SERVER_ERROR);


    private String message;
    private String description;
    private HttpStatus statusCode;

    MessageEnum(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    MessageEnum(String message, String description, HttpStatus statusCode) {
        this.message = message;
        this.description = description;
        this.statusCode = statusCode;
    }

    private static class Holder {
        static Map<String, MessageEnum> MAP = new HashMap<>();
    }

    MessageEnum(String s) {
        Holder.MAP.put(s, this);
    }

    public static MessageEnum find(String val) {
        MessageEnum result = Holder.MAP.get(val);
        if(result == null) {
            throw new IllegalStateException(String.format("Unsupported Message %s.", val));
        }
        return result;
    }
}
