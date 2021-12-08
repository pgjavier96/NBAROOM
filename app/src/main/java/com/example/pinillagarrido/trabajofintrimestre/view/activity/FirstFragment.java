package com.example.pinillagarrido.trabajofintrimestre.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pinillagarrido.trabajofintrimestre.R;
import com.example.pinillagarrido.trabajofintrimestre.databinding.FragmentFirstBinding;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.Player;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.PlayerTeam;
import com.example.pinillagarrido.trabajofintrimestre.view.adapter.PlayerAdapter;
import com.example.pinillagarrido.trabajofintrimestre.viewmodel.PlayerViewModel;

import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        PlayerViewModel pvm = new ViewModelProvider(this).get(PlayerViewModel.class);
        PlayerAdapter playerAdapter = new PlayerAdapter(getContext());

        binding.recyclerView2.setAdapter(playerAdapter);

        //LiveData<List<Pokemon>> listaPokemon = pvm.getPokemons();
        LiveData<List<PlayerTeam>> listaPlayerType = pvm.getAllPlayer();
        listaPlayerType.observe(getViewLifecycleOwner(), players -> {
            playerAdapter.setPokemonList(players);
        });

    binding.btADD.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_createplayerFragment2);
        }
    });
        binding.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_editplayerFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}