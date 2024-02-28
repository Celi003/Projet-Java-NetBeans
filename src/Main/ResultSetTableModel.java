package Main;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Seno04
 */
public class ResultSetTableModel extends AbstractTableModel {

    private ResultSet rs;

    public ResultSetTableModel(ResultSet rs) {
        this.rs = rs;
        fireTableDataChanged();
    }

    public int getColumnCount() {
        try {
            if (rs == null) {
                return 0;
            } else {
                return  rs.getMetaData().getColumnCount();
            }
        } catch (SQLException e) {
            System.out.println("getColumncount  resultset generating error while getting column count");
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int getRowCount() {
        try {
            if (rs == null) {
                return 0;
            } else {
                rs.last();
                return rs.getRow();
            }
        } catch (SQLException e) {
            System.out.println("getrowcount resultset generating error while getting rows count");
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > getRowCount()
                || columnIndex < 0 || columnIndex > getColumnCount()) {
            return null;
        }
        try {
            if (rs == null) {
                return null;
            } else {
                rs.absolute(rowIndex + 1);
                return rs.getObject(columnIndex + 1);
            }
        } catch (SQLException e) {
            System.out.println("getvalueat resultset generating error while fetching rows");
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        try {
            if (rs == null) {
                return null;
            } else {
                // Récupérer le nom de la colonne à partir de l'alias si disponible
                String columnName = rs.getMetaData().getColumnLabel(columnIndex + 1);
                if (columnName == null || columnName.isEmpty()) {
                    // Si l'alias n'est pas disponible, utilisez le nom de la colonne de la table
                    columnName = rs.getMetaData().getColumnName(columnIndex + 1);
                }
                return columnName;
            }
        } catch (SQLException e) {
            System.out.println("getColumnname  resultset generating error while fetching column name");
            System.out.println(e.getMessage());
        }
        return super.getColumnName(columnIndex);
    }
}
