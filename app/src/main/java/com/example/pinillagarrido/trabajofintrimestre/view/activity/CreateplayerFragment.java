package com.example.pinillagarrido.trabajofintrimestre.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pinillagarrido.trabajofintrimestre.R;
import com.example.pinillagarrido.trabajofintrimestre.databinding.FragmentCreatePlayerBinding;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.Player;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.Team;
import com.example.pinillagarrido.trabajofintrimestre.viewmodel.PlayerViewModel;
import com.example.pinillagarrido.trabajofintrimestre.viewmodel.TeamViewModel;


public class CreateplayerFragment extends Fragment {

    private PlayerViewModel pvm;
    private Player player;
    private boolean firstTime = true;
private FragmentCreatePlayerBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = FragmentCreatePlayerBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getViewModel();

        binding.btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CreateplayerFragment.this)
                        .navigate(R.id.action_createplayerFragment2_to_FirstFragment);
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
            });
        binding.btAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player player = getPlayer();
                addPlayer(player);
                NavHostFragment.findNavController(CreateplayerFragment.this)
                        .navigate(R.id.action_createplayerFragment2_to_FirstFragment);

            }
        });

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private Player getPlayer() {
        String name = binding.etName.getText().toString().trim();
        String height = binding.etHeight.getText().toString().trim();
        String weight = binding.etWeight.getText().toString().trim();
        String url = binding.etUrl.getText().toString().trim();
        Team team = (Team) binding.spType.getSelectedItem();
        Player player = new Player();
        player.name = name;
        player.height = Double.parseDouble(height);
        player.weight = Integer.parseInt(weight);
        player.url = url;
        player.idtype = team.id;
        return player;
    }

    private void addPlayer(Player player) {
        pvm.insertPlayer(player);
    }

    private void getViewModel() {
        pvm = new ViewModelProvider(this).get(PlayerViewModel.class);
/*
        pvm.getKalos();
        pvm.getKalosResult().observe(this, s -> {
            //aqui me llega la lista de pokemons
        });

        /*pvm.getInsertResult().observe(this, aLong -> {
            Log.v("xyzyx", "res: " + aLong);
        });*/

        pvm.getInsertResults().observe(getViewLifecycleOwner(), list -> {
            Log.v("xyzyx", "res: " + list);
            if(list.get(0) > 0) {
                if(firstTime) {
                    firstTime = false;
                    alert();
                } else {
                    cleanFields();
                }
            } else {
                Toast.makeText(getContext(), "10 no inserted", Toast.LENGTH_LONG).show();
            }
        });



    }

    private void alert() {
        AlertDialog.Builder builder  = new AlertDialog.Builder(getContext());
        builder.setTitle("Insertar mas?")
                .setMessage("El pokemon se ha insertado correctamente, desea seguir agregando pokemons?")
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cleanFields();
                    }
                });
        builder.create().show();
    }

    private void cleanFields() {
        binding.etUrl.setText("");
        binding.etWeight.setText("");
        binding.etHeight.setText("");
        binding.etName.setText("");
        binding.spType.setSelection(0);
        Toast.makeText(getContext(), "8 inserted", Toast.LENGTH_LONG).show();
    }
   
}