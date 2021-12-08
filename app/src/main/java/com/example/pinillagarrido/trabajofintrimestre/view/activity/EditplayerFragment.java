package com.example.pinillagarrido.trabajofintrimestre.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pinillagarrido.trabajofintrimestre.R;
import com.example.pinillagarrido.trabajofintrimestre.databinding.FragmentCreatePlayerBinding;
import com.example.pinillagarrido.trabajofintrimestre.databinding.FragmentEditplayerBinding;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.Player;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.Team;
import com.example.pinillagarrido.trabajofintrimestre.viewmodel.PlayerViewModel;
import com.example.pinillagarrido.trabajofintrimestre.viewmodel.TeamViewModel;



public class EditplayerFragment extends Fragment {
    private PlayerViewModel pvm;
    private Player player;
    private boolean firstTime = true;
    private FragmentEditplayerBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentEditplayerBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = new Bundle();
        bundle = getArguments();
        player = bundle.getParcelable("player");
        binding.tvIdplayer.setText(String.valueOf(player.id));
        binding.etName.setText(player.name);
        binding.etHeight2.setText(String.valueOf(player.height));
        binding.etWeight2.setText(String.valueOf(player.weight));
        binding.etUrl.setText(player.url);
        Glide.with(this).load(binding.etUrl.getText().toString()).into(binding.ivImage);
        getViewModel(player.idtype);
        binding.btEdit2.setOnClickListener(v -> {
            Player p = getPlayer();
            if (p.isValid()){
                pvm.updatePlayer(p);
                NavHostFragment.findNavController(EditplayerFragment.this)
                        .navigate(R.id.action_editplayerFragment_to_FirstFragment);
            }
        });

        binding.button.setOnClickListener(v -> {
            Player p = getPlayer();
            if (p.isValid()){
                pvm.deletePlayers(p);
                NavHostFragment.findNavController(EditplayerFragment.this)
                        .navigate(R.id.action_editplayerFragment_to_FirstFragment);
            }
        });

        binding.btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(EditplayerFragment.this)
                        .navigate(R.id.action_editplayerFragment_to_FirstFragment);
            }
        });

    }
    private Player getPlayer() {
        String id = binding.tvIdplayer.getText().toString().trim();
        String name = binding.etName.getText().toString().trim();
        String height = binding.etHeight2.getText().toString().trim();
        String weight = binding.etWeight2.getText().toString().trim();
        String url = binding.etUrl.getText().toString().trim();
        Team team = (Team) binding.spType.getSelectedItem();
        Player player = new Player();
        player.name = name;
        player.height = Double.parseDouble(height);
        player.weight = Integer.parseInt(weight);
        player.url = url;
        player.idtype = team.id;
        player.id = Long.parseLong(id);
        return player;
    }

    private void getViewModel(long idtype) {
        pvm = new ViewModelProvider(this).get(PlayerViewModel.class);
        pvm.getInsertResults().observe(getViewLifecycleOwner(), list -> {
            Log.v("xyzyx", "res: " + list);
            if(list.get(0) > 0) {
                if(firstTime) {
                    firstTime = false;
                } else {
                    cleanFields();
                }
            } else {
                Toast.makeText(getContext(), "10 no inserted", Toast.LENGTH_LONG).show();
            }
        });
        TeamViewModel tvm = new ViewModelProvider(this).get(TeamViewModel.class);
        tvm.getTeams().observe(getViewLifecycleOwner(), types -> {
            Team team = new Team();
            team.id = 0;
            team.name = getString(R.string.default_type);
            types.add(0, team);
            ArrayAdapter<Team> adapter =
                    new ArrayAdapter<Team>(getContext(), android.R.layout.simple_spinner_dropdown_item, types);
            binding.spType.setAdapter(adapter);
            binding.spType.setSelection((int) idtype);
        });
    }

    private void cleanFields() {
        binding.etUrl.setText("");
        binding.etWeight2.setText("");
        binding.etHeight2.setText("");
        binding.etName.setText("");
        binding.spType.setSelection(0);
        Toast.makeText(getContext(), "8 inserted", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

