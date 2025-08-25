package br.com.anymarket.sdk.product.dto;

/**
 * DTO para remote integration -
 */
public enum TransmissionStatus {

    WAITING_SYNC("Aguardando Sincronização"),
    SENDING("Enviando"),
    PAUSING("Pausando"),
    CLOSING("Finalizando"),
    ERROR("Erro"),
    ACTIVATING("Ativando"),
    RETRY("Retentativa"),
    SYNCING("Sincronizando"),
    TRANSLATING("Traduzindo"),
    OK("Sincronizado"),
    REMOVING("Removendo");

    private final String name;

    public String getName() {
        return name;
    }

    TransmissionStatus(String name) {
        this.name = name;
    }

    public static TransmissionStatus fromName(String name) {
        if (name != null) {
            for (TransmissionStatus ts : TransmissionStatus.values()) {
                if (name.equalsIgnoreCase(ts.name())) {
                    return ts;
                }
            }
        }
        return null;
    }

}