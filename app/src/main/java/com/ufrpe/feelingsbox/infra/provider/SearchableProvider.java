package com.ufrpe.feelingsbox.infra.provider;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.provider.SearchRecentSuggestions;

public class SearchableProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "com.ufrpe.feelingsbox.infra.provider.SearchableProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public SearchableProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

    public static void salvarSugestao(Context context, String tag){
        SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(context,
                SearchableProvider.AUTHORITY,
                SearchableProvider.MODE);
        searchRecentSuggestions.saveRecentQuery(tag, null);
    }
    public static void limparHistorico(Context context){
        SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(context,
                SearchableProvider.AUTHORITY,
                SearchableProvider.MODE);
        searchRecentSuggestions.clearHistory();
    }
}
