package com.ufrpe.feelingsbox.redesocial.dominio;

import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Sessao {
    private static Sessao instancia = new Sessao();
    private Pessoa pessoaLogada = null;
    private Usuario usuarioLogado = null;
    private PerfilPublico perfillogado = null;
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private List<ActEnum> historicoDeTelas = new ArrayList<>();
    private List<BundleEnum> listaModos = new ArrayList<>();
    private List<Post> listaPosts = new ArrayList<>();

    public static Sessao getInstancia(){

        return instancia;
    }

    public Pessoa getPessoaLogada(){
        return pessoaLogada;
    }

    public void setPessoaLogada(Pessoa novaPessoa) {
        this.pessoaLogada = novaPessoa;
    }

    public Usuario getUsuarioLogado() { return usuarioLogado; }

    public void setUsuarioLogado(Usuario novoUsuario) {
        this.usuarioLogado = novoUsuario;
    }

    public PerfilPublico getPerfillogado() {
        return perfillogado;
    }

    public void setPerfillogado(PerfilPublico perfillogado) {
        this.perfillogado = perfillogado;
    }

    public void invalidarSessao() {
        pessoaLogada = null;
        usuarioLogado = null;
        perfillogado = null;
    }

    public void addUsuario(Usuario usuario){ listaUsuarios.add(usuario); }

    public void limparListaUsuarios(){ listaUsuarios.clear();}

    private int indiceUltimoUsuario(){ return (listaUsuarios.size()-1); }

    public Usuario popUsuario(){
        if(listaUsuarios.isEmpty()){
            return (null);
        }
        Usuario usuario = this.getUltimoUsuario();
        listaUsuarios.remove(this.indiceUltimoUsuario());

        return usuario;
    }

    public Usuario getUltimoUsuario(){
        if(listaUsuarios.isEmpty()){
            return (null);
        }
        return listaUsuarios.get(this.indiceUltimoUsuario());
    }

    public void addHistorico(ActEnum tela){ historicoDeTelas.add(tela); }

    public void limparListaHistorico(){ historicoDeTelas.clear(); }

    private int indiceUltimoHistorico(){ return (historicoDeTelas.size()-1); }

    public ActEnum popHistorico(){
        if(historicoDeTelas.isEmpty()){
            return (null);
        }
        ActEnum tela = this.getUltimoHistorico();
        historicoDeTelas.remove(this.indiceUltimoHistorico());

        return tela;
    }

    public ActEnum getUltimoHistorico(){
        if(historicoDeTelas.isEmpty()){
            return (null);
        }
        return historicoDeTelas.get(this.indiceUltimoHistorico());
    }

    public void addModo(BundleEnum modo){ listaModos.add(modo); }

    public void limparListaModos(){ listaModos.clear(); }

    private int indiceUltimoModo(){ return (listaModos.size()-1); }

    public BundleEnum popModo(){
        if(listaModos.isEmpty()){
            return (null);
        }
        BundleEnum modo = this.getUltimoModo();
        listaModos.remove(this.indiceUltimoModo());

        return modo;
    }

    public BundleEnum getUltimoModo(){
        if(listaModos.isEmpty()){
            return (null);
        }
        return listaModos.get(this.indiceUltimoModo());
    }

    public void addPost(Post post){ listaPosts.add(post); }

    public void limparListaPosts(){ listaPosts.clear();}

    private int indiceUltimoPost(){ return (listaPosts.size()-1); }

    public Post popPost(){
        if(listaPosts.isEmpty()){
            return (null);
        }
        Post post = this.getUltimoPost();
        listaPosts.remove(this.indiceUltimoPost());

        return post;
    }

    public Post getUltimoPost() {
        if (listaPosts.isEmpty()) {
            return (null);
        }
        return listaPosts.get(this.indiceUltimoPost());
    }

    public void limparHistoricoUsuariosModosPosts(){
        this.limparListaUsuarios();
        this.limparListaHistorico();
        this.limparListaModos();
        this.limparListaPosts();
    }
}