package com.ufrpe.feelingsbox.redesocial.redesocialservices;

import android.content.Context;

import com.ufrpe.feelingsbox.infra.ValidacaoService;
import com.ufrpe.feelingsbox.redesocial.dominio.Comentario;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.persistencia.ComentarioDAO;
import com.ufrpe.feelingsbox.redesocial.persistencia.PosTagDAO;
import com.ufrpe.feelingsbox.redesocial.persistencia.PostDAO;
import com.ufrpe.feelingsbox.redesocial.persistencia.RelacaoSegDAO;
import com.ufrpe.feelingsbox.redesocial.persistencia.RelacaoUserTagDAO;
import com.ufrpe.feelingsbox.redesocial.persistencia.SessaoDAO;
import com.ufrpe.feelingsbox.redesocial.persistencia.SugestaoDAO;
import com.ufrpe.feelingsbox.redesocial.persistencia.TagDAO;
import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RedeServices {
    private Sessao sessao = Sessao.getInstancia();
    private Post post;
    private PosTagDAO posTagDAO;
    private SessaoDAO sessaoDAO;
    private PostDAO postDAO;
    private ComentarioDAO comentarioDAO;
    private Context context;
    private Comentario comentario;
    private RelacaoSegDAO relacaoSegDAO;
    private SugestaoDAO sugestaoDAO;
    private TagDAO tagDAO;
    private RelacaoUserTagDAO relacaoUserTagDAO;

    private static final double ZERO = 0;
    private static final int UM = 1;
    private static final int ESCALA = 3;

    public RedeServices(Context context) {
        this.context = context;
    }

    /**
     * Método para salvar o post no banco
     * @param texto
     */

    public void salvarPost(String texto) {
        postDAO = new PostDAO(context);
        post = new Post();
        post.setTexto(texto);
        post.setDataHora();
        post.setIdUsuario(sessao.getUsuarioLogado().getId());
        long idPost = postDAO.inserirPost(post);
        post.setId(idPost);

        TagDAO tagDAO = new TagDAO(context);
        PosTagDAO posTagDAO = new PosTagDAO(context);
        String regexHashTag = "(#\\w+)";

        Pattern pattern = Pattern.compile(regexHashTag);
        Matcher matcher = pattern.matcher(texto);
        while (matcher.find()) {
            String hashTagStr = matcher.group(UM);
            if (tagDAO.getTagTexto(hashTagStr) == null) {
                tagDAO.inserirTag(hashTagStr.toLowerCase());
            }
            posTagDAO.inserirTagIdPost(hashTagStr.toLowerCase(), idPost);
            setTagUser(hashTagStr.toLowerCase(), sessao.getUsuarioLogado().getId());
        }
    }

    /**
     * Método para salvar comentário no banco
     * @param texto Texto do comentário
     * @param idPost Id do post ao qual ele pertence
     */

    public void salvarComentario(String texto, long idPost) {
        comentarioDAO = new ComentarioDAO(context);
        comentario = new Comentario();
        comentario.setTexto(texto);
        comentario.setDataHora();
        comentario.setIdUsuario(sessao.getUsuarioLogado().getId());
        comentario.setIdPost(idPost);
        long idComentario = comentarioDAO.inserirComentario(comentario);
        comentario.setId(idComentario);

        tagDAO = new TagDAO(context);
        posTagDAO = new PosTagDAO(context);
        String regexHashTag = "(#\\w+)";

        Pattern pattern = Pattern.compile(regexHashTag);
        Matcher matcher = pattern.matcher(texto);
        while (matcher.find()) {
            String hashTagStr = matcher.group(UM);
            if (tagDAO.getTagTexto(hashTagStr) == null) {
                tagDAO.inserirTag(hashTagStr.toLowerCase());
            }
            posTagDAO.inserirTagIdPost(hashTagStr.toLowerCase(), idPost);
            setTagUser(hashTagStr.toLowerCase(), sessao.getUsuarioLogado().getId());
        }
    }

    /**
     * Método para exibir posts na timeline geral
     * @return Retorna uma lista de posts do banco
     */

    public List<Post> exibirPosts() {
        postDAO = new PostDAO(context);
        return postDAO.getPostsByOrderId();
    }

    /**
     * Método para deslogar o usuário
     */

    public void finalizarSessao() {
        sessaoDAO = new SessaoDAO(context);
        sessaoDAO.removerPessoa();
        sessao.invalidarSessao();
    }

    /**
     * Método para buscar posts através da tag
     * @param tag Tag pesquisada pelo usuário
     * @return Retorna uma lista de posts que contenham a tag pesquisada
     */

    public List<Post> buscarPosts(String tag) {
        posTagDAO = new PosTagDAO(context);
        return posTagDAO.getPostByTag(tag);
    }

    /**
     * Método para exibir posts dos usuários em seus perfis
     * @param id Id do usuário
     * @return Retorna uma lista de posts que contenham o id do usuário
     */

    public List<Post> exibirPosts(long id) {
        postDAO = new PostDAO(context);
        return postDAO.getPostByUser(id);
    }

    /**
     * Método para exibir comentários de determinado post
     * @param idPost Id do post
     * @return Retorna uma lista com os comentários de determinado post
     */

    public List<Comentario> exibirComentarios(long idPost) {
        comentarioDAO = new ComentarioDAO(context);
        return comentarioDAO.getComentariorioByPost(idPost);
    }

    /**
     * Método para seguir um usuário
     * @param idUser Id do usuário
     * @param idSeguir Id do seguido
     */

    public void seguirUser(long idUser, long idSeguir) {
        relacaoSegDAO = new RelacaoSegDAO(context);
        relacaoSegDAO.inserirRelSeguidores(idUser, idSeguir);
    }

    /**
     * Método para deixar de seguir usuário
     * @param idSeguidor Id do seguidor
     * @param idSeguido Id do seguido
     */

    public void deletarUser(long idSeguidor, long idSeguido) {
        relacaoSegDAO = new RelacaoSegDAO(context);
        relacaoSegDAO.deletarRelSeguidores(idSeguidor, idSeguido);
    }

    /**
     * Método para listar os seguidores do usuário
     * @param id Id do usuário logado
     * @return Retorna lista de seguidores do usuário
     */

    public List<Usuario> listarSeguidores(long id) {
        relacaoSegDAO = new RelacaoSegDAO(context);
        return relacaoSegDAO.getSeguidoresUser(id);
    }

    /**
     * Método para listar os seguidos do usuário
     * @param id Id do usuário logado
     * @return Retorna lista de seguidos do usuário
     */

    public List<Usuario> listarSeguidos(long id) {
        relacaoSegDAO = new RelacaoSegDAO(context);
        return relacaoSegDAO.getSeguidosUser(id);
    }

    /**
     * Método para exibir a quantidade de seguidores do usuário
     * @param id Id do usuário
     * @return Retorna a quantidade de seguidores do usuário
     */

    public long qtdSeguidores(long id) {
        relacaoSegDAO = new RelacaoSegDAO(context);
        return relacaoSegDAO.getQtdSeguidoresUser(id);
    }

    /**
     * Método para exibir a quantidade de seguidos do usuário
     * @param id Id do usuário
     * @return Retorna a quantidade de seguidos do usuário
     */

    public long qtdSeguidos(long id) {
        relacaoSegDAO = new RelacaoSegDAO(context);
        return relacaoSegDAO.getQtdSeguidosUser(id);
    }

    /**
     * Método para verificar se o usuário segue outro que aparece para ele
     * @param idSeguidor Id do suposto seguidor
     * @param idSeguido Id do suposto seguido
     * @return Retorna true ou false para a verificação
     */

    public boolean verificacaoSeguidor(long idSeguidor, long idSeguido) {
        relacaoSegDAO = new RelacaoSegDAO(context);
        return relacaoSegDAO.verificaSeguidor(idSeguidor, idSeguido);
    }

    /**
     * Método para verificar se há uma seção aberta para poder logar automaticamente se
     * for o caso
     * @return Retorna pessoa logada se houver uma, senão retorna null
     */

    public Pessoa verificarSessao() {
        sessaoDAO = new SessaoDAO(context);
        return sessaoDAO.getPessoaLogada();
    }

    /**
     * Método para listar posts mais comentado do dia
     * @return Retorna lista de posts mais comentados do dia
     */

    public List<Post> postsFiltradosComentarios() {
        sugestaoDAO = new SugestaoDAO(context);
        postDAO = new PostDAO(context);
        LinkedHashSet<Post> listaFiltrada = new LinkedHashSet<>();
        listaFiltrada.addAll(sugestaoDAO.getPostsMaisComentadosHoje());
        listaFiltrada.addAll(postDAO.getPostsByOrderId());
        List<Post> listaFiltradaFinal = new ArrayList<>();
        listaFiltradaFinal.addAll(listaFiltrada);
        return listaFiltradaFinal;
    }

    /**
     * Método para gerar uma lista de todas as tags existentes no banco
     * @return Retorna uma lista de todas as tags existentes no banco
     */

    public List<String> listaTags() {
        tagDAO = new TagDAO(context);
        return tagDAO.getTagsByOrder();
    }

    /**
     * Método para exibir a quantidade de comentários no post
     * @param idPost Id do post
     * @return Retorna quantidade de comentários do post
     */

    public long qtdComentariosPost(long idPost) {
        comentarioDAO = new ComentarioDAO(context);
        return comentarioDAO.qtdQuantidadeComentario(idPost);
    }

    /**
     * Métdo para para registrar quando um usuário pesquisa uma tag
     * @param tag Tag pesquisada
     * @param idUser Id do usuário
     */

    public void setTagUser(String tag, long idUser) {
        ValidacaoService validacaoService = new ValidacaoService(context);
        if (validacaoService.verificarTagPesquisada(tag)) {
            relacaoUserTagDAO = new RelacaoUserTagDAO(context);
            relacaoUserTagDAO.inserirRelUserTag(tag.toLowerCase(), idUser);
        }
    }

    /**
     * Cálculo do índice de proximidade entre o vetor da tag pesquisada com o vetor das demais
     * tags do banco utilizando a fórmula do cosseno entre dois vetores
     * @param vetor1 Vetor da tag pesquisada
     * @param vetor2 Vetor de um tag qualquer do banco
     * @return Retorna o índice de proximidade entre ambos os vetores
     */

    private BigDecimal calcAproximacaoTag(ArrayList<Integer> vetor1, ArrayList<Integer> vetor2) {
        int numerador = 0;
        double prodInt1 = 0;
        double prodInt2 = 0;

        for (int i = 0; i < vetor1.size(); i++) {
            numerador += vetor1.get(i) * vetor2.get(i);
            prodInt1 += vetor1.get(i) * vetor1.get(i);
            prodInt2 += vetor2.get(i) * vetor2.get(i);
        }

        double denominador = Math.sqrt(prodInt1) * Math.sqrt(prodInt2);
        BigDecimal big1 = new BigDecimal(numerador);
        BigDecimal big2 = new BigDecimal(denominador);

        if(denominador != ZERO){
            return big1.divide(big2, ESCALA, RoundingMode.UP);
        } else {
            return big2;
        }
    }

    /**
     * Método para gera a matriz de relação entre post e tags na qual a coluna serão vetores
     * @param listaTag Lista de tags existentes no banco
     * @param listaIdPost Lista de id dos posts existentes no banco
     * @return
     */

    private ArrayList<ArrayList<Integer>> gerarMatriz(List<String> listaTag, List<Long> listaIdPost) {
        posTagDAO = new PosTagDAO(context);
        ArrayList<ArrayList<Integer>> matriz = new ArrayList<>();

        for (int linha = 0; linha < listaIdPost.size(); linha++) {
            ArrayList<Integer> arrayLinha = new ArrayList<>();
            for (int coluna = 0; coluna < listaTag.size(); coluna++) {
                arrayLinha.add(posTagDAO.getRelacaoTagPost(listaIdPost.get(linha), listaTag.get(coluna)) ? 1 : 0);
            }
            matriz.add(arrayLinha);
        }
        return  matriz;
    }

    /**
     * Método para pegar a tag mais próxima de uma tag pesquisada por um usuário para recomendá-la
     * @param tag Tag pesquisada
     * @return Retorna uma tag para recomendações
     */

    private String recomendaTag(String tag){
        postDAO = new PostDAO(context);
        tagDAO = new TagDAO(context);
        ArrayList<Long> listaPostId = postDAO.getListaPostId();
        ArrayList<String> listaTags = tagDAO.getListaTags(tag);
        listaTags.add(0,tag);

        ArrayList<ArrayList<Integer>> matriz = gerarMatriz(listaTags, listaPostId);
        ArrayList<BigDecimal> aproximacoes = new ArrayList<>();

        for (int i = 1; i < matriz.size(); i++){
            aproximacoes.add(calcAproximacaoTag(matriz.get(0), matriz.get(i)));
        }

        BigDecimal maiorAprox = Collections.max(aproximacoes);
        int indexMaiorAprox = aproximacoes.indexOf(maiorAprox);
        return listaTags.get(indexMaiorAprox + 1);

    }

    /**
     * Método para pegar a tag mais pesquisada pelo usuário
     * @param idUser Id do ususário
     * @return Retorna tag mais pesquisada
     */

    private String getTagMaisUsada(long idUser){
        relacaoUserTagDAO = new RelacaoUserTagDAO(context);
        return relacaoUserTagDAO.getTagsMaisPesquisadas(idUser);
    }

    /**
     * Método para exibir posts recomendados a partir de uma tag aproximada
     * @param idUser Id do usuário
     * @return Retorna uma lista de posts com a tag recomendada
     */

    public ArrayList<Post> gerarPostsTagAproximada(long idUser){
        String tagMaisUsada = getTagMaisUsada(idUser);
        String tagAproximada = recomendaTag(tagMaisUsada);
        posTagDAO = new PosTagDAO(context);
        return posTagDAO.getPostByTag(tagAproximada);
    }

    /**
     * Método para exibir posts dos seguidos do usuário na timeline favoritos
     * @param idUser Id do usuário logado
     * @return Retorna lista de posts dos seguidos do usuário
     */

    public List<Post> getPostsFavoritos(long idUser){
        postDAO = new PostDAO(context);
        return postDAO.getPostFavoritos(idUser);
    }

    /**
     * Método para verificar se o usuário é novato
     * @param idUser Id do usuário logado
     * @return Retorna true se o usuário for novato e false se não for
     */

    public boolean isNovato(long idUser){
        relacaoUserTagDAO = new RelacaoUserTagDAO(context);
        String tagUser = relacaoUserTagDAO.getTagsMaisPesquisadas(idUser);
        return tagUser == null;
    }
}