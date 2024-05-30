package fr.yodrack.matriceled;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import java.util.List;

public class GridFragment extends Fragment {

    private CustomGridView customGridView;
    private String listeDesCases;
    private int x_start;
    private int y_start;
    private int cellWidth;
    private int cellHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        customGridView = view.findViewById(R.id.custom_grid_view);

        if (getArguments() != null) {
            listeDesCases = getArguments().getString("listeDesCases");
            x_start = getArguments().getInt("x_start", 0);
            y_start = getArguments().getInt("y_start", 0);
            cellWidth = getArguments().getInt("cellWidth", 50);
            cellHeight = getArguments().getInt("cellHeight", 50);

            List<CustomGridView.Point> points = CustomGridView.convertToPointsList(listeDesCases);
            customGridView.setInitialSelectedPoints(points);
            customGridView.setXStart(x_start);
            customGridView.setYStart(y_start);
            customGridView.setCellWidth(cellWidth);
            customGridView.setCellHeight(cellHeight);
        }

        return view;
    }

    public List<CustomGridView.Point> getSelectedPoints() {
        return customGridView.getSelectedPoints();
    }
}
