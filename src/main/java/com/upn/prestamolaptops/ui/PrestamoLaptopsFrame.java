package com.upn.prestamolaptops.ui;

import com.upn.prestamolaptops.model.Laptop;
import com.upn.prestamolaptops.model.ResultadoOperacion;
import com.upn.prestamolaptops.service.PrestamoService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class PrestamoLaptopsFrame extends JFrame {

    private static final Color COLOR_FONDO = new Color(242, 242, 242);
    private static final Color COLOR_TARJETA = Color.WHITE;
    private static final Color COLOR_BORDE_TARJETA = new Color(185, 185, 185);
    private static final Color COLOR_HEADER_BG = new Color(28, 28, 28);
    private static final Color COLOR_UPN_AMARILLO = new Color(245, 196, 0);
    private static final Color COLOR_UPN_AMARILLO_HOVER = new Color(230, 180, 0);
    private static final Color COLOR_UPN_AMARILLO_BORDE = new Color(145, 118, 0);
    private static final Color COLOR_BTN_SECUNDARIO = new Color(224, 224, 224);
    private static final Color COLOR_BTN_SECUNDARIO_BORDE = new Color(158, 158, 158);
    private static final Color COLOR_TEXTO = new Color(34, 34, 34);
    private static final Color COLOR_SUBTEXTO = new Color(115, 115, 115);
    private static final Color COLOR_TABLA_HEADER = new Color(214, 214, 214);
    private static final Color COLOR_TABLA_SELECCION = new Color(255, 243, 196);
    private static final Color COLOR_OK_BG = new Color(232, 245, 233);
    private static final Color COLOR_OK_BORDER = new Color(129, 199, 132);
    private static final Color COLOR_OK_TEXT = new Color(46, 125, 50);
    private static final Color COLOR_ERR_BG = new Color(255, 235, 238);
    private static final Color COLOR_ERR_BORDER = new Color(229, 115, 115);
    private static final Color COLOR_ERR_TEXT = new Color(198, 40, 40);
    private static final Color COLOR_INFO_BG = new Color(248, 249, 250);
    private static final Color COLOR_INFO_BORDER = new Color(204, 204, 204);
    private static final Color COLOR_INFO_TEXT = new Color(85, 85, 85);

    private static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 15);
    private static final Font FONT_SECCION = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_AYUDA = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_BOTON_PRIMARIO = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_BOTON_SECUNDARIO = new Font("Segoe UI", Font.PLAIN, 13);

    private final PrestamoService service;

    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JComboBox<Laptop> cboLaptop;
    private JButton btnPrestar;
    private JButton btnLimpiar;
    private JButton btnDevolver;
    private JTable tblLaptops;
    private DefaultTableModel modeloTabla;
    private JLabel lblMensaje;

    public PrestamoLaptopsFrame(PrestamoService service) {
        this.service = service;
        inicializarVentana();
        construirInterfaz();
        registrarEventos();
        recargarDatos("LT-005");
        mostrarMensajeNeutral("Ingrese los datos del estudiante y seleccione una laptop para registrar el préstamo.");
    }

    private void inicializarVentana() {
        setTitle("Préstamo de Laptops UPN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(780, 560));
        setSize(820, 590);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new BorderLayout());
    }

    private void construirInterfaz() {
        add(crearBarraSuperior(), BorderLayout.NORTH);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(COLOR_FONDO);
        contenido.setBorder(new EmptyBorder(16, 18, 16, 18));

        contenido.add(crearPanelFormularios());
        contenido.add(Box.createVerticalStrut(14));
        contenido.add(crearPanelBotones());
        contenido.add(Box.createVerticalStrut(16));
        contenido.add(crearPanelInventario());
        contenido.add(Box.createVerticalStrut(14));
        contenido.add(crearPanelMensaje());

        add(contenido, BorderLayout.CENTER);
    }

    private JPanel crearBarraSuperior() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(COLOR_HEADER_BG);
        barra.setBorder(new EmptyBorder(11, 18, 11, 18));

        JLabel titulo = new JLabel("Préstamo de Laptops UPN");
        titulo.setForeground(COLOR_UPN_AMARILLO);
        titulo.setFont(FONT_HEADER);
        barra.add(titulo, BorderLayout.WEST);

        return barra;
    }

    private JPanel crearPanelFormularios() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 16, 0));
        panel.setBackground(COLOR_FONDO);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 155));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(crearTarjetaEstudiante());
        panel.add(crearTarjetaLaptop());

        return panel;
    }

    private JPanel crearTarjetaEstudiante() {
        JPanel tarjeta = crearTarjetaBase("Datos del estudiante");

        JPanel cuerpo = new JPanel(new GridBagLayout());
        cuerpo.setBackground(COLOR_TARJETA);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 0, 4, 8);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(FONT_LABEL);
        lblNombre.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        cuerpo.add(lblNombre, gbc);

        txtNombre = crearCampoTexto();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cuerpo.add(txtNombre, gbc);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setFont(FONT_LABEL);
        lblCodigo.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.insets = new Insets(4, 0, 4, 8);
        gbc.fill = GridBagConstraints.NONE;
        cuerpo.add(lblCodigo, gbc);

        txtCodigo = crearCampoTexto();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cuerpo.add(txtCodigo, gbc);

        JLabel lblFormato = new JLabel("Formato: U + 7 dígitos (8 caracteres)");
        lblFormato.setFont(FONT_AYUDA);
        lblFormato.setForeground(COLOR_SUBTEXTO);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(6, 0, 0, 0);
        cuerpo.add(lblFormato, gbc);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    private JPanel crearTarjetaLaptop() {
        JPanel tarjeta = crearTarjetaBase("Laptop a prestar");

        JPanel cuerpo = new JPanel(new GridBagLayout());
        cuerpo.setBackground(COLOR_TARJETA);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblLaptop = new JLabel("Laptop:");
        lblLaptop.setFont(FONT_LABEL);
        lblLaptop.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(4, 0, 4, 8);
        gbc.fill = GridBagConstraints.NONE;
        cuerpo.add(lblLaptop, gbc);

        cboLaptop = new JComboBox<>();
        cboLaptop.setFont(FONT_INPUT);
        cboLaptop.setBackground(Color.WHITE);
        cboLaptop.setPreferredSize(new Dimension(220, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cuerpo.add(cboLaptop, gbc);

        JLabel lblSoloDisponibles = new JLabel("Solo se listan laptops en estado Disponible");
        lblSoloDisponibles.setFont(FONT_AYUDA);
        lblSoloDisponibles.setForeground(COLOR_SUBTEXTO);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 0, 0);
        cuerpo.add(lblSoloDisponibles, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        cuerpo.add(Box.createVerticalGlue(), gbc);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    private JPanel crearTarjetaBase(String titulo) {
        JPanel tarjeta = new JPanel(new BorderLayout(0, 8));
        tarjeta.setBackground(COLOR_TARJETA);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_TARJETA, 1),
                new EmptyBorder(12, 16, 12, 16)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(FONT_SECCION);
        lblTitulo.setForeground(COLOR_TEXTO);
        tarjeta.add(lblTitulo, BorderLayout.NORTH);

        return tarjeta;
    }

    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(FONT_INPUT);
        campo.setForeground(COLOR_TEXTO);
        campo.setPreferredSize(new Dimension(200, 30));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170, 170, 170), 1),
                new EmptyBorder(3, 8, 3, 8)
        ));
        return campo;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        panel.setBackground(COLOR_FONDO);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(new EmptyBorder(0, -12, 0, 0));

        btnPrestar = crearBoton("Prestar", COLOR_UPN_AMARILLO, COLOR_UPN_AMARILLO_HOVER, COLOR_UPN_AMARILLO_BORDE, FONT_BOTON_PRIMARIO);
        btnLimpiar = crearBoton("Limpiar", COLOR_BTN_SECUNDARIO, new Color(210, 210, 210), COLOR_BTN_SECUNDARIO_BORDE, FONT_BOTON_SECUNDARIO);
        btnDevolver = crearBoton("Devolver", COLOR_BTN_SECUNDARIO, new Color(210, 210, 210), COLOR_BTN_SECUNDARIO_BORDE, FONT_BOTON_SECUNDARIO);

        panel.add(btnPrestar);
        panel.add(btnLimpiar);
        panel.add(btnDevolver);

        return panel;
    }

    private JButton crearBoton(String texto, final Color fondo, final Color fondoHover, final Color borde, Font fuente) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? fondoHover : fondo);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 6, 6);
                g2.setColor(borde);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        boton.setFont(fuente);
        boton.setForeground(COLOR_TEXTO);
        boton.setPreferredSize(new Dimension(145, 34));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private JPanel crearPanelInventario() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(COLOR_FONDO);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblInventario = new JLabel("Inventario de laptops");
        lblInventario.setFont(FONT_SECCION);
        lblInventario.setForeground(COLOR_TEXTO);
        panel.add(lblInventario, BorderLayout.NORTH);

        String[] columnas = {"Código", "Marca / Modelo", "Estado", "Estudiante"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLaptops = new JTable(modeloTabla);
        tblLaptops.setFont(FONT_INPUT);
        tblLaptops.setForeground(COLOR_TEXTO);
        tblLaptops.setRowHeight(28);
        tblLaptops.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblLaptops.setShowGrid(true);
        tblLaptops.setGridColor(new Color(220, 220, 220));
        tblLaptops.setIntercellSpacing(new Dimension(1, 1));
        tblLaptops.getTableHeader().setReorderingAllowed(false);

        JTableHeader header = tblLaptops.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(COLOR_TABLA_HEADER);
        header.setForeground(COLOR_TEXTO);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 30));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        DefaultTableCellRenderer renderizadorCelda = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(isSelected ? COLOR_TABLA_SELECCION : Color.WHITE);
                c.setForeground(COLOR_TEXTO);
                setBorder(new EmptyBorder(0, 8, 0, 8));
                return c;
            }
        };

        for (int i = 0; i < tblLaptops.getColumnCount(); i++) {
            tblLaptops.getColumnModel().getColumn(i).setCellRenderer(renderizadorCelda);
        }

        tblLaptops.getColumnModel().getColumn(0).setPreferredWidth(110);
        tblLaptops.getColumnModel().getColumn(1).setPreferredWidth(250);
        tblLaptops.getColumnModel().getColumn(2).setPreferredWidth(140);
        tblLaptops.getColumnModel().getColumn(3).setPreferredWidth(140);

        JScrollPane scroll = new JScrollPane(tblLaptops);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_TARJETA, 1));
        scroll.setPreferredSize(new Dimension(740, 175));

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelMensaje() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(FONT_LABEL);
        lblMensaje.setOpaque(true);
        lblMensaje.setPreferredSize(new Dimension(740, 38));

        panel.add(lblMensaje, BorderLayout.CENTER);
        return panel;
    }

    private void registrarEventos() {
        btnPrestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarPrestamo();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarLimpiar();
            }
        });

        btnDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarDevolucion();
            }
        });

        tblLaptops.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    sincronizarComboConTabla();
                }
            }
        });
    }

    private void manejarPrestamo() {
        Laptop laptopSeleccionada = (Laptop) cboLaptop.getSelectedItem();
        String codigoLaptop = laptopSeleccionada == null ? null : laptopSeleccionada.getCodigo();

        ResultadoOperacion resultado = service.registrarPrestamo(
                txtNombre.getText(),
                txtCodigo.getText(),
                codigoLaptop
        );

        if (resultado.isExitoso()) {
            recargarDatos(codigoLaptop);
            mostrarMensajeExito(resultado.getMensaje());
        } else {
            mostrarMensajeError(resultado.getMensaje());
        }
    }

    private void manejarDevolucion() {
        String codigoLaptop = obtenerCodigoLaptopSeleccionadaEnTabla();
        if (codigoLaptop == null) {
            Laptop prestada = service.buscarLaptopPorEstudiante(txtCodigo.getText());
            if (prestada != null) {
                codigoLaptop = prestada.getCodigo();
            }
        }

        ResultadoOperacion resultado = service.registrarDevolucion(codigoLaptop, txtCodigo.getText());

        if (resultado.isExitoso()) {
            recargarDatos(codigoLaptop);
            mostrarMensajeExito(resultado.getMensaje());
        } else {
            mostrarMensajeError(resultado.getMensaje());
        }
    }

    private void manejarLimpiar() {
        txtNombre.setText("");
        txtCodigo.setText("");
        tblLaptops.clearSelection();
        if (cboLaptop.getItemCount() > 0) {
            cboLaptop.setSelectedIndex(0);
        }
        mostrarMensajeNeutral("Campos limpiados.");
        txtNombre.requestFocusInWindow();
    }

    private void sincronizarComboConTabla() {
        String codigoSeleccionado = obtenerCodigoLaptopSeleccionadaEnTabla();
        if (codigoSeleccionado == null) {
            return;
        }
        for (int i = 0; i < cboLaptop.getItemCount(); i++) {
            Laptop item = cboLaptop.getItemAt(i);
            if (item != null && codigoSeleccionado.equals(item.getCodigo())) {
                cboLaptop.setSelectedIndex(i);
                break;
            }
        }
    }

    private String obtenerCodigoLaptopSeleccionadaEnTabla() {
        int fila = tblLaptops.getSelectedRow();
        if (fila < 0) {
            return null;
        }
        Object valor = modeloTabla.getValueAt(fila, 0);
        return valor == null ? null : valor.toString();
    }

    private void recargarDatos(String codigoResaltar) {
        cargarComboDisponibles(codigoResaltar);
        cargarTablaInventario(codigoResaltar);
    }

    private void cargarComboDisponibles(String codigoPreferido) {
        DefaultComboBoxModel<Laptop> modeloCombo = new DefaultComboBoxModel<>();
        List<Laptop> disponibles = service.listarLaptopsDisponibles();
        Laptop seleccionada = null;

        for (Laptop laptop : disponibles) {
            modeloCombo.addElement(laptop);
            if (codigoPreferido != null && codigoPreferido.equals(laptop.getCodigo())) {
                seleccionada = laptop;
            }
        }

        cboLaptop.setModel(modeloCombo);
        if (seleccionada != null) {
            cboLaptop.setSelectedItem(seleccionada);
        } else if (modeloCombo.getSize() > 0) {
            cboLaptop.setSelectedIndex(0);
        }
    }

    private void cargarTablaInventario(String codigoResaltar) {
        modeloTabla.setRowCount(0);
        List<Laptop> inventario = service.listarLaptops();
        int filaSeleccionar = -1;

        for (int i = 0; i < inventario.size(); i++) {
            Laptop laptop = inventario.get(i);
            String estudiante = laptop.getCodigoEstudianteAsignado() == null
                    ? "\u2014"
                    : laptop.getCodigoEstudianteAsignado();

            modeloTabla.addRow(new Object[]{
                laptop.getCodigo(),
                laptop.getMarcaModelo(),
                laptop.getEstado().getEtiqueta(),
                estudiante
            });

            if (codigoResaltar != null && codigoResaltar.equals(laptop.getCodigo())) {
                filaSeleccionar = i;
            }
        }

        if (filaSeleccionar >= 0) {
            tblLaptops.setRowSelectionInterval(filaSeleccionar, filaSeleccionar);
        }
    }

    private void mostrarMensajeExito(String texto) {
        aplicarEstiloMensaje(texto, COLOR_OK_BG, COLOR_OK_BORDER, COLOR_OK_TEXT);
    }

    private void mostrarMensajeError(String texto) {
        aplicarEstiloMensaje(texto, COLOR_ERR_BG, COLOR_ERR_BORDER, COLOR_ERR_TEXT);
    }

    private void mostrarMensajeNeutral(String texto) {
        aplicarEstiloMensaje(texto, COLOR_INFO_BG, COLOR_INFO_BORDER, COLOR_INFO_TEXT);
    }

    private void aplicarEstiloMensaje(String texto, Color fondo, Color borde, Color colorTexto) {
        lblMensaje.setText(texto);
        lblMensaje.setBackground(fondo);
        lblMensaje.setForeground(colorTexto);
        Border bordeCompuesto = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borde, 1),
                new EmptyBorder(6, 12, 6, 12)
        );
        lblMensaje.setBorder(bordeCompuesto);
    }
}
