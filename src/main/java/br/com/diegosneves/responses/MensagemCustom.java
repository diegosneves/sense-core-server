package br.com.diegosneves.responses;

public record MensagemCustom(String mensagem) {

    public static MensagemCustom of(final String mensagem) {
        return new MensagemCustom(mensagem);
    }
}
