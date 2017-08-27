package com.ufrpe.feelingsbox.infra.provider;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.provider.SearchRecentSuggestions;

/**
 * Classe com as constantes para configuração do @see {@link SearchRecentSuggestionsProvider}.
 */

public class SearchableProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "com.ufrpe.feelingsbox.infra.provider.SearchableProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public SearchableProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

    /**
     * Método que registra a Tag no banco de dados do SearchRecentSuggestionsProvider.
     * @param context - Contexto da aplicação.
     * @param tag - Tag pesquisada pelo @see {@link com.ufrpe.feelingsbox.usuario.dominio.Usuario}.
     */

    public static void salvarSugestao(Context context, String tag){
        SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(context,
                SearchableProvider.AUTHORITY,
                SearchableProvider.MODE);
        searchRecentSuggestions.saveRecentQuery(tag, null);
    }

    /**
     * Método que apaga todos os registros do banco de dados do SearchRecentSuggestionsProvider.
     * @param context - Contexto da aplicação.
     */

    public static void limparHistorico(Context context){
        SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(context,
                SearchableProvider.AUTHORITY,
                SearchableProvider.MODE);
        searchRecentSuggestions.clearHistory();
    }
}
