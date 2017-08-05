package com.ufrpe.feelingsbox.infra.scripts;

import android.content.Context;

import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;

public class PostScript {

    public static void gerarPosts(Context context){
        RedeServices redeServices = new RedeServices(context);

        redeServices.salvarPost("As melhores coisas da vida são gratuitas: abraços, sorrisos," +
                                " amigos, beijos, família, dormir, amor, risos e boas lembranças.");
        redeServices.salvarPost("É pra frente que se anda, é pra cima que se olha e é lutando que" +
                                " se conquista.");
        redeServices.salvarPost("Treine sua mente para ver o lado bom de qualquer situação.");
        redeServices.salvarPost("Creio de coração que tudo nesta vida se renova. Tudo recomeça," +
                                " tudo renasce, tudo avança. Creio no bem e na força maior que nos" +
                                " move. Creio em dias de paz e que a felicidade acontece quando nos" +
                                " colocamos a favor de todo o bem, em tudo e para todos.");
        redeServices.salvarPost("Um dia você ainda vai olhar para trás e ver que os problemas eram," +
                                " na verdade, os degraus que te levaram à vitória.");
        redeServices.salvarPost("Cuidemos do nosso coração porque é de lá que sai o que é bom e" +
                                " ruim, o que constrói e destrói.");


    }
}
