package views;

import domain.*;
import data.Persistencia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InvalidPropertiesFormatException;

public class AgregarAnimalView extends JFrame {
    private JTextField nombreEspecieField;
    private JComboBox<TipoAlimentacion> tipoAlimentacionComboBox;
    private JTextField edadField;
    private JTextField pesoField;
    private JTextField valorFijoField;
    private JButton guardarButton;
    private JButton cancelarButton;

    public AgregarAnimalView() {
        setTitle("Agregar Nuevo Animal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Inicializar componentes
        JLabel especieLabel = new JLabel("Especie:");
        especieLabel.setBounds(20, 20, 100, 25);
        add(especieLabel);

        nombreEspecieField = new JTextField();
        nombreEspecieField.setBounds(150, 20, 200, 25);
        add(nombreEspecieField);

        JLabel tipoAlimentacionLabel = new JLabel("Tipo de Alimentación:");
        tipoAlimentacionLabel.setBounds(20, 60, 150, 25);
        add(tipoAlimentacionLabel);

        tipoAlimentacionComboBox = new JComboBox<>(TipoAlimentacion.values());
        tipoAlimentacionComboBox.setBounds(150, 60, 200, 25);
        add(tipoAlimentacionComboBox);

        JLabel edadLabel = new JLabel("Edad:");
        edadLabel.setBounds(20, 100, 100, 25);
        add(edadLabel);

        edadField = new JTextField();
        edadField.setBounds(150, 100, 200, 25);
        add(edadField);

        JLabel pesoLabel = new JLabel("Peso:");
        pesoLabel.setBounds(20, 140, 100, 25);
        add(pesoLabel);

        pesoField = new JTextField();
        pesoField.setBounds(150, 140, 200, 25);
        add(pesoField);

        JLabel valorFijoLabel = new JLabel("Valor Fijo (Herbívoro):");
        valorFijoLabel.setBounds(20, 180, 150, 25);
        add(valorFijoLabel);

        valorFijoField = new JTextField();
        valorFijoField.setBounds(150, 180, 200, 25);
        add(valorFijoField);

        // Botones
        guardarButton = new JButton("Guardar");
        guardarButton.setBounds(50, 220, 100, 30);
        add(guardarButton);

        cancelarButton = new JButton("Cancelar");
        cancelarButton.setBounds(200, 220, 100, 30);
        add(cancelarButton);
        
    

        // Acciones de los botones
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarAnimal();
            }
        });
        
      
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }   
        });
    }

  private void agregarAnimal() {
    String nombreEspecie = nombreEspecieField.getText();
    TipoAlimentacion tipoAlimentacion = (TipoAlimentacion) tipoAlimentacionComboBox.getSelectedItem();
    int edad = Integer.parseInt(edadField.getText());
    double peso = Double.parseDouble(pesoField.getText());
    double valorFijo = 0;

    if (tipoAlimentacion == TipoAlimentacion.HERBIVORO) {
        valorFijo = Double.parseDouble(valorFijoField.getText());
    }

    // Crear la especie
    Especie especie = new Especie(nombreEspecie, tipoAlimentacion, tipoAlimentacion == TipoAlimentacion.CARNIVORO ? 0.2 : 0);
    
    // Crear el sector (se puede modificar para seleccionar un sector existente)
    Sector sector = new Sector(1, -26.250724, -65.522827, 10, tipoAlimentacion, new Empleado("Empleado", "12345678", "Domicilio"));

    try {
        Mamifero nuevoAnimal;
        if (tipoAlimentacion == TipoAlimentacion.CARNIVORO) {
            nuevoAnimal = new Carnivoro(edad, peso, especie, sector);
        } else {
            nuevoAnimal = new Herbivoro(edad, peso, especie, sector, valorFijo);
        }
        Persistencia.getAnimales().add(nuevoAnimal);
        JOptionPane.showMessageDialog(this, "Animal agregado exitosamente.");

        // Actualizar la tabla en ListarAnimalesView
        ListarAnimalesView listarAnimalesView = new ListarAnimalesView();
        listarAnimalesView.actualizarTabla();
        listarAnimalesView.setVisible(true);

        dispose();
    } catch (InvalidPropertiesFormatException e) {
        JOptionPane.showMessageDialog(this, "Error al agregar el animal: " + e.getMessage());
    }
}
}