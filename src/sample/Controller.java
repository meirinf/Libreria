package sample;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import sample.Objetos.Llibre;
import sample.Objetos.Prestec;
import sample.Objetos.Soci;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 53638138e on 20/01/17.
 */
public class Controller {


    public DAO DAO = new DAO();

    public String dateError;
    public String tipoModificar;
    public String tipoNuevo;
    public String tipoBusqueda;

    // Elementos de JAVA FX

    public ScrollPane scrollPane;
    public Text scrollText;
    public Text textoInfoSeccion;
    public Text textoAyudaFechas;
    public TextField campoBusqueda;

    // Campos que actualizaremos en cada sección

    public TextField campoTexto1;
    public TextField campoTexto2;
    public TextField campoTexto3;
    public TextField campoTexto4;
    public TextField campoTexto5;
    public TextField campoTexto6;

    public Button buttonGuardar;
    public Button buttonBuscar;
    public Button buttonModificar;
    public Button buttonBorrarItem;

    public ListView listView;

    public ObservableList<String> observableLlibres = FXCollections.observableArrayList();
    public ObservableList<String> observableSocis = FXCollections.observableArrayList();
    public ObservableList<String> observablePrestec = FXCollections.observableArrayList();

    private ArrayList<Llibre> llibres = new ArrayList<>();
    private ArrayList<Soci> socis = new ArrayList<>();
    private ArrayList<Prestec> prestecs = new ArrayList<>();

    // Metodos

    public void initialize() {
        descargarDatosBBDD();
    }

    public void descargarDatosBBDD() {
//Aqui saca y muestra los elementos de la base de datos
        //Este es para Libros
        try {
            llibres = DAO.obtenirLlibres();

            observableLlibres.clear();

            for(int iterador = 0; iterador < llibres.size(); iterador++){
                observableLlibres.add(llibres.get(iterador).toString());
            }
        }
        //Esto es para socios
        catch (Exception noBooks){}

        try {
            socis = DAO.obtenirSocis();

            observableSocis.clear();

            for(int iterador = 0; iterador < socis.size(); iterador++){
                observableSocis.add(socis.get(iterador).toString());
            }
        }
        //Esto es para miembros
        catch (Exception noMembers) {}

        try {
            observablePrestec.clear();

            prestecs = DAO.obtenirPrestecs();

            for(int iterador = 0; iterador < prestecs.size(); iterador++){
                observablePrestec.add(prestecs.get(iterador).toString());
            }
        }
        catch (Exception noLoans){}
    }

    // Metodos para listar

    public void listaLlibres(ActionEvent actionEvent) {

        scrollText.setText("Lista de libros : ");
        scrollPane.setVisible(true);

        // Comprobamos nuestro array y mostramos lo que contiene
        if (llibres.size() == 0){
            listView.setItems(null);
            scrollText.setText(scrollText.getText() + "\n\n- No hay libros");
        }
        else {
            listView.setItems(observableLlibres);
            listView.setVisible(true);
        }

        // Para modificar cualquier libro se le hace clic encima
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tipoModificar = "libro";
                int idListView = listView.getSelectionModel().getSelectedIndex();
                mostrarCamposModificar(idListView);
            }
        });
    }

    public void listaSocis(ActionEvent actionEvent) {

        scrollText.setText("Lista de socios : ");
        scrollPane.setVisible(true);

        // Comprobamos nuestro array y mostramos lo que contiene

        if (socis.size() == 0){
            listView.setItems(null);
            scrollText.setText(scrollText.getText() + "\n\n- No hay socios");
        }
        else {
            listView.setItems(observableSocis);
            listView.setVisible(true);
        }

        // Para modificar cualquier socio se le hace clic encima
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tipoModificar = "socio";
                int idListView = listView.getSelectionModel().getSelectedIndex();
                mostrarCamposModificar(idListView);
            }
        });
    }

    public void listaPrestecs(ActionEvent actionEvent) {

        scrollText.setText("Lista de prestamos : ");
        scrollPane.setVisible(true);

        // Comprobamos nuestro array y mostramos lo que contiene
        if (prestecs.size() == 0){
            listView.setItems(null);
            scrollText.setText(scrollText.getText() + "\n\n- No hay prestamos");
        }
        else {
            listView.setItems(observablePrestec);
            listView.setVisible(true);
        }

        // Para modificar cualquier socio se le hace clic encima

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tipoModificar = "prestamo";
                buttonBorrarItem.setVisible(true);
            }
        });
    }

    public void listaLibrosFueraPlazo(ActionEvent actionEvent) {

        scrollText.setText("\n   Listar Libros que estan fuera de tiempo : ");
        ObservableList<String> librosFueraPlazo = FXCollections.observableArrayList();
        scrollPane.setVisible(true);

        Date today = new Date();
        for (int iterador = 0; iterador < prestecs.size(); iterador++) {
            //Aqui el metodo que utilizo es que si la fecha de devolución es diferente a la fecha final es un moroso
            today = prestecs.get(iterador).getFechaDebol();
            if (!today.equals(prestecs.get(iterador).getDataFinal())||today.after(prestecs.get(iterador).getDataFinal())) {
                librosFueraPlazo.add(prestecs.get(iterador).getLlibre().toString() +
                        "\n     Fecha de entrega : " + prestecs.get(iterador).getFechaDebol().toString()+
                        "\n     Fecha limite : " + prestecs.get(iterador).getDataFinal().toString());
            }
        }

        // Miramos qué hemos encontrado y segun si hay reusltados mostramos una cosa u otra
        if(librosFueraPlazo.size() == 0){
            listView.setItems(null);
            scrollText.setText("    No hayresultados ");
        }
        else {
            listView.setItems(librosFueraPlazo);
            listView.setVisible(true);
        }
    }

    public void listaSociosFueraPlazo(ActionEvent actionEvent) {

        scrollText.setText("\n   Lista de socios con libros fuera de plazo ");
        ObservableList<String> sociosFueraPlazo = FXCollections.observableArrayList();
        scrollPane.setVisible(true);

        Date today = new Date();
        for (int iterador = 0; iterador < prestecs.size(); iterador++) {
            today = prestecs.get(iterador).getFechaDebol();
            if (!today.equals(prestecs.get(iterador).getDataFinal())||today.before(prestecs.get(iterador).getDataFinal())) {
                sociosFueraPlazo.add(prestecs.get(iterador).getSoci().toString() +
                        "\n     Fecha de entrega : " + prestecs.get(iterador).getFechaDebol().toString()+
                        "\n     Fecha limite : " + prestecs.get(iterador).getDataFinal().toString());
            }
        }

        // Miramos qué hemos encontrado y segun si hay reusltados mostramos una cosa u otra
        if(sociosFueraPlazo.size() == 0){
            listView.setItems(null);
            scrollText.setText("    No hayresultados ");
        }
        else {
            listView.setItems(sociosFueraPlazo);
            listView.setVisible(true);
        }
    }

    // Metodos para anyadir datos a la BBDD

    private void afegirLlibre(){

        // Comprobamos si se han rellenado todos los campos
        if (campoTexto1.getText().equals("") || campoTexto2.getText().equals("") || campoTexto3.getText().equals("") || campoTexto4.getText().equals("") || campoTexto5.getText().equals("") || campoTexto6.getText().equals("")) {
            textoInfoSeccion.setText("\nSe tienen que llenar correctamente todos los datos ");
        }
        else {
            // Creamos un nuevo libro
            Llibre llibre = new Llibre();

            ocultarTodo();      // Ocultamos todos los campos para que no puedan haber modificaciones a la hora de añadir

            // Y le asignamos su información
            llibre.setTitol(campoTexto1.getText());
            llibre.setAutor(campoTexto2.getText());
            llibre.setEditorial(campoTexto3.getText());
            llibre.setNombrePagines(campoTexto4.getText());
            llibre.setAnyEdicio(campoTexto5.getText());
            llibre.setNombreExemplars(campoTexto6.getText());

            observableLlibres.add(llibre.toString());
            llibres.add(llibre);
            DAO.afegirLlibre(llibre);

            ocultarTodo();
            listaLlibres(null);
        }
    }

    private void afegirSoci() {

        // Comprobamos si se han rellenado todos los campos
        if (campoTexto1.getText().equals("") || campoTexto2.getText().equals("") ||campoTexto3.getText().equals("") ||campoTexto4.getText().equals("") ||campoTexto5.getText().equals("")) {
            textoInfoSeccion.setText("\nSe tienen que llenar correctamente todos los datos");
        }
        else {
            // Creamos un nuevo socio
            Soci soci = new Soci();

            ocultarTodo();

            // Y le asignamos su información

            soci.setNom(campoTexto1.getText());
            soci.setCognom(campoTexto2.getText());
            soci.setEdat(campoTexto3.getText());
            soci.setDireccio(campoTexto4.getText());
            soci.setTelefon(campoTexto5.getText());

            observableSocis.add(soci.toString());
            socis.add(soci);
            DAO.afegirSoci(soci);

            ocultarTodo();
            listaSocis(null);
        }
    }

    private void afegirPrestec() {

        // Comprobamos si se han rellenado todos los campos
        if (campoTexto1.getText().equals("") || campoTexto2.getText().equals("") ||campoTexto3.getText().equals("") ||campoTexto4.getText().equals("")||campoTexto5.getText().equals("")) {
            textoInfoSeccion.setText("\nSe tienen que llenar correctamente todos los datos ");
        }
        else {
            // Creamos un nuevo prestamo
            Prestec prestec = new Prestec();

            // Buscamos el libro introducido en nuestro array de libros y se lo asignamos al prestamo
            for (int iterador = 0; iterador < llibres.size(); iterador++) {
                if (llibres.get(iterador).getTitol().toLowerCase().equals(campoTexto1.getText().toLowerCase())) {
                    prestec.setLlibre(llibres.get(iterador));
                }
            }

            // Buscamos el socio introducido en nuestro array de socios y se lo asignamos al prestamo
            for (int iterador = 0; iterador < socis.size(); iterador++) {
                if (socis.get(iterador).getNom().toLowerCase().equals(campoTexto2.getText().toLowerCase())) {
                    prestec.setSoci(socis.get(iterador));
                }
            }

            try  {
                DateFormat formatData = new SimpleDateFormat("mm/dd/yyyy");

                // Extraemos las fechas de nuestro campo de texto y le damos el formato correcto
                Date dataInici = formatData.parse(campoTexto3.getText());
                Date dataFinal = formatData.parse(campoTexto4.getText());
                Date dataDevol = formatData.parse(campoTexto5.getText());

                ocultarTodo();       // Primero escondemos todos los objetos

                prestec.setDataInici(dataInici);
                prestec.setDataFinal(dataFinal);
                prestec.setFechaDebol(dataDevol);

                // Y se comparan las fechas

                if(dataInici.equals(dataFinal)) {
                    dateError = "Fechas Iguales";
                    throw new InvalidDateException();
                }

                if (dataInici.after(dataFinal)) {
                    dateError = "Fechas Incorrectas";
                    throw new InvalidDateException();
                }

                try  {
                    observablePrestec.add(prestec.toString());
                    prestecs.add(prestec);
                    DAO.afegirPrestec(prestec);
                    listaPrestecs(null);

                } catch (Exception one) {
                    textoInfoSeccion.setText("\n No esta disponible ");
                }
            }
            catch(InvalidDateException two) {

                if (dateError.equals("Fechas Iguales"))  {
                    textoAyudaFechas.setText("\n ERROR: las fechas son iguales.");
                }

                if (dateError.equals("Fechas Incorrectas")) {
                    textoAyudaFechas.setText("\n La fecha final no puede ser menor que la de inicio ");
                }
            }
            catch (Exception three) {
                textoInfoSeccion.setText("\n El formato es incorrecto. Introduce este formato \"DD/MM/YYYY\"\"");
            }
        }
    }

    // Metodos para mostrar distintos campos y secciones de la interfaz

    public void crearLlibre(ActionEvent actionEvent) {

        tipoNuevo = "libro";    // Asignamos a la variable que lo que vamos a crear es un libro

        textoInfoSeccion.setText("\n Introduce el nuevo libro : ");
        campoTexto1.setPromptText("Titulo");
        campoTexto2.setPromptText("Autor ");
        campoTexto3.setPromptText("Editorial");
        campoTexto4.setPromptText("Numero de paginas");
        campoTexto5.setPromptText("Año de edicion ");
        campoTexto6.setPromptText("Numero de ejemplares");

        mostarCamposCrear(true);
    }

    public void crearSoci(ActionEvent actionEvent)  {

        tipoNuevo = "socio";    // Asignamos a la variable que lo que vamos a crear es un socio

        textoInfoSeccion.setText("\n INTRODUCE LOS DATOS DEL SOCIO : ");
        campoTexto1.setPromptText("Nombre : ");
        campoTexto2.setPromptText("Apellidos : ");
        campoTexto3.setPromptText("Edad : ");
        campoTexto4.setPromptText("Direccion : ");
        campoTexto5.setPromptText("Telefono : ");

        mostarCamposCrear(true);
    }

    public void crearPrestec(ActionEvent actionEvent) {

        tipoNuevo = "prestamo";

        textoInfoSeccion.setText("\n INTRODUCE LOS DATOS DEL PRESTAMO : ");
        campoTexto1.setPromptText("Titulo del libro : ");
        campoTexto2.setPromptText("Nombre del socio : ");
        campoTexto3.setPromptText("Fecha de inicio  del prestamo DD/MM/YYYY : ");
        campoTexto4.setPromptText("Fecha final del prestamo Final DD/MM/YYYY");
        campoTexto5.setPromptText("Fecha de entrega del libro DD/MM/YY");
        textoAyudaFechas.setText("\n Los textos de las fechas tienen que estar formados de est forma :  \"  DD/MM/YYYY  \"");

        mostarCamposCrear(true);
        campoTexto5.setVisible(true);
        textoAyudaFechas.setVisible(true);
    }

    public void guardarDatos(ActionEvent actionEvent){

        // Depende del valor de la variable, se guardaran los campos de crear un libro, un socio o un prestamo.

        if (tipoNuevo.equals("libro")) {
            afegirLlibre();
        }
        else if (tipoNuevo.equals("socio")) {
            afegirSoci();
        }
        else if (tipoNuevo.equals("prestamo")) {
            afegirPrestec();
        }
    }

    public void buscarLibroPorTitulo(ActionEvent actionEvent) {

        tipoBusqueda = "busquedaPorTitulo";

        // Establecemos el texto del campo y el titulo
        textoInfoSeccion.setText("\n Por titulo.");
        campoBusqueda.setPromptText("Titulo");

        mostrarCamposBusqueda();
        textoInfoSeccion.setVisible(true);
    }

    public void buscarLibroPorAutor(ActionEvent actionEvent) {

        tipoBusqueda = "busquedaPorAutor";

        // Establecemos el texto del campo y el titulo
        textoInfoSeccion.setText("\n Buscar libros por autor.");
        campoBusqueda.setPromptText("Autor");

        mostrarCamposBusqueda();
        textoInfoSeccion.setVisible(true);
    }

    public void buscarSocioPorNombre(ActionEvent actionEvent) {

        tipoBusqueda = "busquedaPorNombre";

        // Establecemos el texto del campo y el titulo
        textoInfoSeccion.setText("\nBuscar socios por nombre");
        campoBusqueda.setPromptText("Nombre");

        mostrarCamposBusqueda();
        textoInfoSeccion.setVisible(true);
    }

    public void buscarSocioPorApellido(ActionEvent actionEvent) {

        tipoBusqueda = "busquedaPorApellido";

        // Establecemos el texto del campo y el titulo
        textoInfoSeccion.setText("\n Buscar socios por apellido.");
        campoBusqueda.setPromptText("Apellido");

        mostrarCamposBusqueda();
        textoInfoSeccion.setVisible(true);
    }

    // Buttons

    public void buscar(ActionEvent actionEvent) {

        if (tipoBusqueda.equals("busquedaPorTitulo")) {

            // Damos el texto correspondiente, hacemos el observable para guardar los resultados de la busqueda y mostramos la seccion
            scrollText.setText("\n  Resultado '" + campoBusqueda.getText().toLowerCase() + "'");
            ObservableList<String> busquedaTitulo = FXCollections.observableArrayList(); // ObservableList para nuestro listView
            scrollPane.setVisible(true);

            for (int iterador = 0; iterador < llibres.size(); iterador++) {
                if (llibres.get(iterador).getTitol().toLowerCase().equals(campoBusqueda.getText().toLowerCase())) {
                    busquedaTitulo.add(llibres.get(iterador).toString());
                }
            }

            // Miramos qué hemos encontrado y segun si hay reusltados mostramos una cosa u otra
            if(busquedaTitulo.size() == 0){
                scrollText.setText("  No hay resultados con esta busqueda ");
            }
            else {
                listView.setItems(busquedaTitulo);
                listView.setVisible(true);
            }
        }
        else if (tipoBusqueda.equals("busquedaPorAutor")) {

            // Damos el texto correspondiente, hacemos el observable para guardar los resultados de la busqueda y mostramos la seccion
            scrollText.setText("\n  Resultado :   '" + campoBusqueda.getText().toLowerCase() + "'");
            ObservableList<String> busquedaAutor = FXCollections.observableArrayList();

            // Recorremos el array buscando libros en el que el autor coincida
            for (int iterador = 0; iterador < llibres.size(); iterador++) {
                if (llibres.get(iterador).getAutor().toLowerCase().equals(campoBusqueda.getText().toLowerCase())) {
                    busquedaAutor.add(llibres.get(iterador).toString());
                }
            }

            // Miramos qué hemos encontrado y segun si hay reusltados mostramos una cosa u otra
            if(busquedaAutor.size() == 0){
                scrollText.setText(" No hay resultados con esta busqueda");
            }
            else {
                listView.setItems(busquedaAutor);
                listView.setVisible(true);
            }
        }
        else if (tipoBusqueda.equals("busquedaPorNombre")) {

            // Damos el texto correspondiente, hacemos el observable para guardar los resultados de la busqueda y mostramos la seccion
            scrollText.setText("\n  Resultado :    '" + campoBusqueda.getText().toLowerCase() + "'");
            ObservableList<String> busquedaNombre = FXCollections.observableArrayList();
            scrollPane.setVisible(true);

            for (int iterador = 0; iterador < socis.size(); iterador++) {
                if (socis.get(iterador).getNom().toLowerCase().equals(campoBusqueda.getText().toLowerCase())) {
                    busquedaNombre.add(socis.get(iterador).toString());
                }
            }

            // Miramos qué hemos encontrado y segun si hay reusltados mostramos una cosa u otra
            if(busquedaNombre.size() == 0){
                scrollText.setText("  No hay resultados con esta busqueda");
            }
            else {
                listView.setItems(busquedaNombre);
                listView.setVisible(true);
            }
        }
        else if (tipoBusqueda.equals("busquedaPorApellido")) {

            // Damos el texto correspondiente, hacemos el observable para guardar los resultados de la busqueda y mostramos la seccion
            scrollText.setText("\n  Resultado :    '" + campoBusqueda.getText().toLowerCase() + "'");
            ObservableList<String> busquedaApellidos = FXCollections.observableArrayList(); // ObservableList para nuestro listView
            scrollPane.setVisible(true);

            for (int iterador = 0; iterador < socis.size(); iterador++) {
                if (socis.get(iterador).getCognom().toLowerCase().equals(campoBusqueda.getText().toLowerCase())){
                    busquedaApellidos.add(socis.get(iterador).toString());
                }
            }

            // Miramos qué hemos encontrado y segun si hay reusltados mostramos una cosa u otra
            if(busquedaApellidos.size() == 0){
                scrollText.setText("  No hay resultados con esta busqueda");
            }
            else {
                listView.setItems(busquedaApellidos);
                listView.setVisible(true);
            }
        }
    }

    public void modificar(ActionEvent actionEvent) {

        if (tipoModificar.equals("libro")) {

            int idSelected = listView.getSelectionModel().getSelectedIndex();

            Llibre llibre = new Llibre();

            // Y le asignamos su información
            llibre.setId(llibres.get(idSelected).getId());
            llibre.setTitol(campoTexto1.getText());
            llibre.setNombreExemplars(campoTexto2.getText());
            llibre.setEditorial(campoTexto3.getText());
            llibre.setNombrePagines(campoTexto4.getText());
            llibre.setAnyEdicio(campoTexto5.getText());
            llibre.setAutor(campoTexto6.getText());

            llibres.set(idSelected, llibre);
            observableLlibres.set(idSelected, llibre.toString());
            DAO.modificarLlibre(llibre);

            ocultarTodo();
            listaLlibres(null);
        }
        else if(tipoModificar.equals("socio")){

            int idSelected = listView.getSelectionModel().getSelectedIndex();

            Soci soci = new Soci();

            // Y le asignamos su información
            soci.setId(socis.get(idSelected).getId());
            soci.setNom(campoTexto1.getText());
            soci.setCognom(campoTexto2.getText());
            soci.setEdat(campoTexto3.getText());
            soci.setDireccio(campoTexto4.getText());
            soci.setTelefon(campoTexto5.getText());

            socis.set(idSelected, soci);
            observableSocis.set(idSelected, soci.toString());
            DAO.modificarSoci(soci);

            ocultarTodo();
            listaLlibres(null);
        }
    }

    public void borrarItem(ActionEvent actionEvent) {

        int idSelected = listView.getSelectionModel().getSelectedIndex();

        // Para eliminar libros, socios o prestamos
        if(tipoModificar.equals("libro")){
            if(DAO.eliminarLlibre(llibres.get(idSelected))){
                llibres.remove(idSelected);
                observableLlibres.remove(idSelected);
                ocultarTodo();
            }
        }
        else if(tipoModificar.equals("socio")){
            if(DAO.eliminarSoci(socis.get(idSelected))){
                socis.remove(idSelected);
                observableSocis.remove(idSelected);
                ocultarTodo();
            }
        }
        else if(tipoModificar.equals("prestamo")){
            if(DAO.eliminarPrestec(prestecs.get(idSelected))){
                prestecs.remove(idSelected);
                observablePrestec.remove(idSelected);
                ocultarTodo();
            }
        }
    }

    // Métodos para mostrar campos y mostrar la interfaz

    public void mostrarCamposModificar(int idListView){

       // El id del list view será igual a la posición en el arrayList del item seleccionado

        if(tipoModificar.equals("libro")){
            tipoNuevo = "libro";

            // Seteamos todos los campos con los datos del libro que queremos modificar
            campoTexto1.setText(llibres.get(idListView).getTitol());
            campoTexto2.setText(llibres.get(idListView).getNombreExemplars());
            campoTexto3.setText(llibres.get(idListView).getEditorial());
            campoTexto4.setText(llibres.get(idListView).getNombrePagines());
            campoTexto5.setText(llibres.get(idListView).getAnyEdicio());
            campoTexto6.setText(llibres.get(idListView).getAutor());

            ocultarTodo();                    // Ocultamos todo
            mostarCamposCrear(false);         // Y mostramos los campos

            // La interfaz de modificar será igual a la de crear per con el botón distinto
            buttonGuardar.setVisible(false);
            buttonModificar.setVisible(true);
            buttonBorrarItem.setVisible(true);
            textoInfoSeccion.setVisible(false);
            buttonModificar.requestFocus();
        }
        else if(tipoModificar.equals("socio")){
            tipoNuevo = "socio"; // Si lo que queremos modificar es un socio

            // Seteamos todos los campos con los datos del socio que queremos modificar
            campoTexto1.setText(socis.get(idListView).getNom());
            campoTexto2.setText(socis.get(idListView).getCognom());
            campoTexto3.setText(socis.get(idListView).getEdat());
            campoTexto4.setText(socis.get(idListView).getDireccio());
            campoTexto5.setText(socis.get(idListView).getEdat());

            ocultarTodo();
            mostarCamposCrear(false);

            // La interfaz de modificar será igual a la de crear per con el botón distinto
            buttonGuardar.setVisible(false);
            buttonModificar.setVisible(true);
            buttonBorrarItem.setVisible(true);
            buttonModificar.requestFocus();
        }
        else if(tipoModificar.equals("prestamo")){

            buttonBorrarItem.setVisible(true);
            textoInfoSeccion.setVisible(false);
            ocultarTodo();

        }

    }

    public void mostarCamposCrear(boolean limpiarCampos) {

        // Si el booleano limpiar campos es true, antes de mostrarlos, limpiaremos los fields

        ocultarTodo();

        buttonGuardar.requestFocus();

        if(limpiarCampos == true){
            // Limpiamos los campos
            campoTexto1.clear();
            campoTexto2.clear();
            campoTexto3.clear();
            campoTexto4.clear();
        }

        // Hacemos visible los elementos
        scrollPane.setVisible(false);
        campoTexto1.setVisible(true);
        campoTexto2.setVisible(true);
        campoTexto3.setVisible(true);
        campoTexto4.setVisible(true);
        buttonGuardar.setVisible(true);
        textoInfoSeccion.setVisible(true);

        // Según lo que estemos mostrando será necesario enseñar un campo u otro

        if (tipoNuevo.equals("socio")) {
            if(limpiarCampos == true){
                campoTexto5.clear();
            }
            campoTexto5.setVisible(true);
        }
        else if (tipoNuevo.equals("libro")) {
            if(limpiarCampos == true){
                campoTexto5.clear();
                campoTexto6.clear();
            }

            campoTexto5.setVisible(true);
            campoTexto6.setVisible(true);
        }
    }

    public void mostrarCamposBusqueda() {

        ocultarTodo();

        campoBusqueda.clear();
        campoBusqueda.setVisible(true);
        buttonBuscar.setVisible(true);
        buttonBuscar.requestFocus();
    }
//Esta clase sirve para ocultar botones y vistas de jafx

    public void ocultarTodo() {

        scrollPane.setVisible(false);
        textoInfoSeccion.setVisible(false);
        campoBusqueda.setVisible(false);
        campoTexto1.setVisible(false);
        campoTexto2.setVisible(false);
        campoTexto3.setVisible(false);
        campoTexto4.setVisible(false);
        campoTexto5.setVisible(false);
        campoTexto6.setVisible(false);
        buttonBuscar.setVisible(false);
        buttonGuardar.setVisible(false);
        buttonModificar.setVisible(false);
        buttonBorrarItem.setVisible(false);
        textoAyudaFechas.setVisible(false);
        listView.setVisible(false);
    }

    // Otros metodos

    public void close(ActionEvent actionEvent) {    // Cierra la APP
        Platform.exit();
    }
//Este metodo se encarga de borrar los elementos de la base de datos
    public void borrarTodo(ActionEvent actionEvent) {

        if (DAO.eliminarTotsElsPrestecs()){
            prestecs.clear();
        }
        if (DAO.eliminarTotsElsSocis()) {
            socis.clear();
        }
        if (DAO.eliminarTotsElsLlibres()) {
            llibres.clear();
        }
    }

    //Dialogo de la aplicación
    public void about(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Biblioteca v0.1");
        alert.setHeaderText("Biblioteca v0.1");
        alert.setContentText(" Estas en la biblioteca virtual." +
                "\nPodras modificar, añadir, prestamos, socios y libros " +
                "\nDesarrolado por Mireia Fernández Casals " +
                "\n               " +
                "\n               ");

        alert.showAndWait();
    }


    // Clase auxiliar para gestionar las excepciones
    public class InvalidDateException extends Exception {
        public InvalidDateException(){

        }
    }
}