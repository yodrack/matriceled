package fr.yodrack.matriceled;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridFragment gridFragment;
    private String listeDesCases = "A1,B2,C3,D4,E5,F6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize and load the fragment
        gridFragment = new GridFragment();
        Bundle bundle = new Bundle();
        bundle.putString("listeDesCases", listeDesCases);
        bundle.putInt("x_start", 0);  // Example values for x_start
        bundle.putInt("y_start", 18); // Example values for y_start
        bundle.putInt("cellWidth", 84);  // Example values for cell width
        bundle.putInt("cellHeight", 84); // Example values for cell height
        gridFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, gridFragment);
        fragmentTransaction.commit();

        // Set up the validate button
        Button validateButton = findViewById(R.id.validate_button);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onValidateButtonClick();
            }
        });
    }

    private void onValidateButtonClick() {
        List<CustomGridView.Point> selectedPoints = gridFragment.getSelectedPoints();
        StringBuilder result = new StringBuilder();
        for (CustomGridView.Point point : selectedPoints) {
            String pointLabel = "" + (char) ('A' + point.col) + (18 - point.row);
            result.append(pointLabel).append(", ");
        }

        if (result.length() > 0) {
            result.setLength(result.length() - 2); // Remove the last comma
        }

        Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
    }
}
