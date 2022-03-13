package com.evanemran.animex.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evanemran.animex.DetailsActivity;
import com.evanemran.animex.R;
import com.evanemran.animex.adapters.AnimeListAdapter;
import com.evanemran.animex.listeners.ClickListener;
import com.evanemran.animex.listeners.ResponseListener;
import com.evanemran.animex.manager.RequestManager;
import com.evanemran.animex.models.AnimeResponse;
import com.evanemran.animex.models.Document;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

public class AnimeFragment extends Fragment {
    View view;
    RecyclerView recycler_home;
    RequestManager manager;
    AnimeListAdapter animeListAdapter;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_anime, container, false);
        recycler_home = view.findViewById(R.id.recycler_home);

        progressBar = (ProgressBar) view.findViewById(R.id.loader);
        Sprite doubleBounce = new Wave();
        progressBar.setIndeterminateDrawable(doubleBounce);

        manager = new RequestManager(getContext());

        manager.getAnime(animeResponseResponseListener);
        return view;
    }

    private final ResponseListener<AnimeResponse> animeResponseResponseListener = new ResponseListener<AnimeResponse>() {
        @Override
        public void didFetch(AnimeResponse response, String message) {
            recycler_home.setHasFixedSize(true);
            recycler_home.setLayoutManager(new GridLayoutManager(getContext(), 3));
            animeListAdapter = new AnimeListAdapter(getContext(), response.documents, clickListener);
            recycler_home.setAdapter(animeListAdapter);
            progressBar.setVisibility(View.GONE);
            recycler_home.setVisibility(View.VISIBLE);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    };

    private final ClickListener<Document> clickListener = new ClickListener<Document>() {
        @Override
        public void onClicked(Document object) {
            startActivity(new Intent(getActivity(), DetailsActivity.class)
            .putExtra("data", object));
        }
    };

}
