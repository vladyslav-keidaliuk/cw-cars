package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Frame extends JFrame implements ItemListener {

    private Date _data_delivery_ ;
    SpinnerModel dateModel = new SpinnerDateModel();
    private JComboBox brands,brands_tab2, models,models_tab2;
    private String _brand_ , _brand_tab2_ , _model_, _model_tab2_ ;
    private static int _price_;
    private  JButton save = new JButton("Зберегти");
    private JTextField price = new JTextField(6);
    private int count_tab2 = -1 , note_number = 0;
    private Auto tab2_auto ;



    public Frame()
    {
        setTitle("Автомобілі");
        setSize(700, 600);
        setResizable(false);

        JTabbedPane tab = new JTabbedPane();
        JPanel tab1 = new JPanel();
        JPanel tab2 = new JPanel();
        JPanel tab3 = new JPanel();

        Font font = new Font("Arial",Font.BOLD,20);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date initialDate, earliestDate, latestDate;

        _data_delivery_= calendar.getTime();

        initialDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -39);
        earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 117);
        latestDate = calendar.getTime();

        JSpinner spinDay1, spinDay2,spinDay3;



        SpinnerModel dayModel1 = new SpinnerDateModel(initialDate,
                earliestDate,
                latestDate,
                Calendar.MONTH);

        SpinnerModel dayModel2 = new SpinnerDateModel(initialDate,
                earliestDate,
                latestDate,
                Calendar.MONTH);

        SpinnerModel dayModel3 = new SpinnerDateModel(initialDate,
                earliestDate,
                latestDate,
                Calendar.MONTH);


        spinDay1 = new JSpinner(dayModel1);
        spinDay1.setEditor(new JSpinner.DateEditor(spinDay1, "dd.MM.yyyy"));

        spinDay2 = new JSpinner(dayModel2);// относится ко 2-й вкладке
        spinDay2.setEditor(new JSpinner.DateEditor(spinDay2, "dd.MM.yyyy"));

        spinDay3 = new JSpinner(dayModel3);// относится к 3-й вкладке
        spinDay3.setEditor(new JSpinner.DateEditor(spinDay3, "dd.MM.yyyy"));


        spinDay1.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                dateModel = spinDay1.getModel();
                _data_delivery_ = ((SpinnerDateModel)dateModel).getDate();
            }
        });



        JLabel warning = new JLabel("");
        warning.setForeground(Color.RED);
        JLabel inf = new JLabel("Ціна(USD):");
        price.setText("");
        price.setEditable(false);

        price.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                    price.setEditable(true);
                    save.setEnabled(true);
                    warning.setText("");
                }
                else if (ke.getKeyCode()== KeyEvent.VK_BACK_SPACE) {
                    price.setEditable(true); save.setEnabled(true); warning.setText("");
                }

                else {
                    price.setEditable(false);
                    save.setEnabled(false);
                    warning.setText("Дозволяється вводити лише цифри (0-9) !");
                }
            }
        });


        save.setEnabled(false);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (price.getText().equals("")){
                    warning.setText("Помилка, поле не заповнено!");
                }
                else if (price.getText().length() >= 9)warning.setText("Ціна однієї автівки не може перевищувати 99 млн$");
                else if (_brand_ == null & _model_ == null ){
                }
                else {
                    tab.setEnabledAt(2,true);
                    tab.setEnabledAt(1,true);
                    if (Objects.equals(price.getText(), ""))_price_ = 0;
                   else _price_ = Integer.parseInt(price.getText());
                    Auto.AddAuto(new Auto( _brand_, _model_, _price_, _data_delivery_));
                }
                price.setText("");
            }
        });

        String[] brands_name_list = {"Обрати","Acura","Audi","BMW","Mercedes"}; // БРЕНДЫ

        brands = new JComboBox(brands_name_list);
        brands.addItemListener(this);

        models = new JComboBox();
        models.addItemListener(this);
        models.setEnabled(false);


        JTextArea tab1_area = new JTextArea(13,24);
        tab1_area.setEditable(false);

        JButton min_max_price = new JButton("Мінімальна та максимальна вартість автомобіля");

        min_max_price.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Auto.Get_Size_ArrList_Auto()!=0){
                    tab1_area.setText(Auto.Min_Max_Price_Car());
                    tab1_area.setForeground(Color.BLACK);
                }

                else{
                    tab1_area.setText("Помилка, немає автівок!");
                    tab1_area.setForeground(Color.RED);
                }

            }
        });

        /////////////Tab1 set fonts///////
        inf.setFont(font);
        price.setFont(font);
        warning.setFont(font);
        save.setFont(font);
        brands.setFont(font);
        models.setFont(font);
        spinDay1.setFont(font);
        min_max_price.setFont(font);
        tab1_area.setFont(font);
        ////////////////////////////////

        tab1.add(brands);
        tab1.add(models);
        tab1.add(inf);
        tab1.add(price);
        tab1.add(warning);
        tab1.add(spinDay1);// дата
        tab1.add(save);
        tab1.add(min_max_price);
        tab1.add(tab1_area);

        ////////////////////////////////////////////////
                        //TAB2//
        ///////////////////////////////////////////////

        JLabel note = new JLabel();

        JLabel brand = new JLabel("Марка : ");
        JTextField brand_tab2 = new JTextField(10);
        brand_tab2.setEditable(false);

        JLabel model = new JLabel("Модель : ");
        JTextField model_tab2 = new JTextField(10);
        model_tab2.setEditable(false);

        JLabel price2 = new JLabel("Ціна ");
        JTextField price_tab2 = new JTextField(7);

        JLabel warning2 = new JLabel("");
        warning2.setForeground(Color.RED);



        JButton left = new JButton("<<");
        JButton right = new JButton(">>");
        JButton refresh = new JButton("Оновити");
        JButton save_edit = new JButton("Зберегти");
        note.setText("Запис № : " + note_number);
        JButton delete_tab2 = new JButton("Видалити");
        delete_tab2.setEnabled(false);

        JRadioButton edit_button = new JRadioButton("Редагувати");
        edit_button.setEnabled(false);


        price_tab2.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                    price_tab2.setEditable(true);
                    save_edit.setEnabled(true);
                    warning2.setText("");
                }
                else if (ke.getKeyCode()== KeyEvent.VK_BACK_SPACE) {
                    price_tab2.setEditable(true); save_edit.setEnabled(true); warning2.setText("");
                }
                else {
                    price_tab2.setEditable(false);
                    save_edit.setEnabled(false);
                    warning2.setText("Дозволяється вводити лише цифри (0-9) !");
                }
            }
        });

        brands_tab2 = new JComboBox(brands_name_list);
        brands_tab2.addItemListener(this);
        brands_tab2.setEnabled(false);

        models_tab2 = new JComboBox();
        models_tab2.addItemListener(this);
        models_tab2.setEnabled(false);

        brands_tab2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String[] acura = {"ILX","MDX","RDX","TLX"};
                String[] audi = {"A2","A5","RS7","Q5","Q7","Q8","e-tron","TT"};
                String[] bmw = {"M2","M3","M4","M5","M7","M8","i3","i8","X1","X3","X4","X5","X6","X7","Z3","Z4"};
                String[] mercedes = {"A-Class","B-Class","C-Class","E-Class","V-Class","S-Class","EQA"
                        ,"EQB","EQC","EQE","EQV","EQS","CLA","CLS","GLA","GLB","GLC","GLS","GLE",
                        "GLC AMG","GLC AMG COUPE","Maybach"};



                if (e.getSource() == brands_tab2){
                    if (brands_tab2.getSelectedItem().equals("Обрати")){
                        save_edit.setEnabled(false);
                        models_tab2.setEnabled(false);
                    }

                    else if (brands_tab2.getSelectedItem().equals("Acura")){
                        models_tab2.setEnabled(true);
                        save_edit.setEnabled(true);
                        models_tab2.removeAllItems();
                        for (int i = 0; i < acura.length;i++){
                            models_tab2.addItem(acura[i]);
                        }
                    }

                    else if (brands_tab2.getSelectedItem().equals("Audi")){
                        models_tab2.setEnabled(true);
                        save_edit.setEnabled(true);
                        models_tab2.removeAllItems();
                        for (int i = 0; i < audi.length;i++){
                            models_tab2.addItem(audi[i]);
                        }
                    }

                    else if (brands_tab2.getSelectedItem().equals("BMW")){
                        models_tab2.setEnabled(true);
                        save_edit.setEnabled(true);
                        models_tab2.removeAllItems();
                        for (int i = 0; i < bmw.length; i ++){
                            models_tab2.removeItem(bmw[i]);
                            models_tab2.addItem(bmw[i]);
                        }
                    }else if (brands_tab2.getSelectedItem().equals("Mercedes")){
                        models_tab2.setEnabled(true);
                        save_edit.setEnabled(true);
                        models_tab2.removeAllItems();
                        for (int i = 0; i< mercedes.length; i ++){
                            models_tab2.addItem(mercedes[i]);
                        }
                    }
                }

                models_tab2.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        _model_tab2_ = (String) models_tab2.getSelectedItem();
                    }

                });
                brands_tab2.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        _brand_tab2_ = (String) brands_tab2.getSelectedItem();
                    }
                });
            }
        });



        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Auto.Get_Size_ArrList_Auto()!=0) {
                    if (note_number < Auto.Get_Size_ArrList_Auto()) {
                        right.setEnabled(true);
                    }
                }
                if (Auto.Get_Size_ArrList_Auto()== 0) right.setEnabled(false);
            }
        });

        brand_tab2.setText("None");
        model_tab2.setText("None");
        price_tab2.setText("---");
        price_tab2.setEditable(false);
        left.setEnabled(false);
        spinDay2.setEnabled(false);
        save_edit.setEnabled(false);


        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spinDay2.setEnabled(false);
                    note_number -=1;
                edit_button.setEnabled(true);
                    note.setText("Запис № : " + note_number);

                    left.setEnabled(true);
                    count_tab2 -=1;

                    tab2_auto = Auto.GetAuto(count_tab2);

                    brand_tab2.setText(tab2_auto.GetBrand());
                    model_tab2.setText(tab2_auto.GetModel());
                    price_tab2.setText(tab2_auto.GetPrice());
                    spinDay2.setValue(tab2_auto.GetDate());

                    if (count_tab2 == 0) left.setEnabled(false);
                    if (count_tab2 != (Auto.Get_Size_ArrList_Auto() -1) ) right.setEnabled(true);

            }
        });

    save_edit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (price_tab2.getText().equals("")){
                warning2.setText("Помилка, поле не заповнено!");
            }

            else if (price_tab2.getText().length() >= 9)warning2.setText(
                    "Ціна однієї автівки не може перевищувати 99 млн$");

            else if (_brand_tab2_ == null && _model_tab2_ == null){
                brand_tab2.setText("Не обрано!");
                model_tab2.setText("Не обрано!");
                brand_tab2.setForeground(Color.RED);
                model_tab2.setForeground(Color.RED);
            }

            else if (_brand_tab2_ != null && _model_tab2_ != null && !Objects.equals(price_tab2.getText(), "")){
            tab2_auto.SetBrand(_brand_tab2_);
            tab2_auto.SetModel(_model_tab2_);
            brand_tab2.setText(_brand_tab2_);
            brand_tab2.setForeground(Color.BLACK);
            model_tab2.setText(_model_tab2_);
            model_tab2.setForeground(Color.BLACK);
            tab2_auto.SetPrice(price_tab2.getText());
            tab2_auto.SetDate((Date) spinDay2.getValue());

            }


        }
    });

        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spinDay2.setEnabled(false);
                edit_button.setEnabled(true);
                note_number ++;
                note.setText("Запис № : " + note_number);
                count_tab2 ++;
                if (count_tab2 == 0) left.setEnabled(false);
                if (count_tab2 >0 ) left.setEnabled(true);

                tab2_auto = Auto.GetAuto(count_tab2);

                brand_tab2.setText(tab2_auto.GetBrand());
                model_tab2.setText(tab2_auto.GetModel());
                price_tab2.setText(tab2_auto.GetPrice());
                spinDay2.setValue(tab2_auto.GetDate());

                if (count_tab2 == (Auto.Get_Size_ArrList_Auto() -1)) right.setEnabled(false);
            }
        });


        delete_tab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    edit_button.setSelected(false);
                    edit_button.setEnabled(false);
                    brands_tab2.setEnabled(false);
                    models_tab2.setEnabled(false);
                    delete_tab2.setEnabled(false);
                    price_tab2.setEditable(false);
                    save_edit.setEnabled(false);
                if (Auto.Get_Size_ArrList_Auto()==1){
                if (count_tab2 == 0){
                    Auto.DeleteAuto(0);
                    brand_tab2.setText("None");
                    model_tab2.setText("None");
                    price_tab2.setText("---");
                    note_number -=1;
                    note.setText("Запис № : "+note_number);
                    count_tab2-=1;
                    }
                }
                else if (Auto.Get_Size_ArrList_Auto()!= 0 ){
                Auto.DeleteAuto(count_tab2);
                        if (count_tab2 != Auto.Get_Size_ArrList_Auto()){
                            right.setEnabled(false);
                        }
                    brand_tab2.setText("None");
                    model_tab2.setText("None");
                    price_tab2.setText("---");
                }
            }
        });



         edit_button.addItemListener(new ItemListener() {
             @Override
             public void itemStateChanged(ItemEvent e) {
                 if (edit_button.isSelected()){
                     edit_button.setForeground(Color.RED);
                     delete_tab2.setEnabled(true);
                     brands_tab2.setEnabled(true);
                     models_tab2.setEnabled(true);
                     spinDay2.setEnabled(true);
                     if (brand_tab2.getText().equals("None") && price_tab2.getText().equals("---")){
                         price_tab2.setEditable(false);
                         save_edit.setEnabled(false);
                         delete_tab2.setEnabled(false);
                         spinDay2.setEnabled(false);


                     }
                     else {
                         price_tab2.setEditable(true);
                         delete_tab2.setEnabled(true);
                     }

                 }
                 else{
                     save_edit.setEnabled(false);
                     brands_tab2.setEnabled(false);
                     models_tab2.setEnabled(false);
                     price_tab2.setEditable(false);
                     delete_tab2.setEnabled(false);
                     spinDay2.setEnabled(false);
                     edit_button.setForeground(Color.BLACK);
                 }

             }
         });

        //////////TAB2 set fonts//////////
            note.setFont(font);
            brand.setFont(font);
            brand_tab2.setFont(font);
            model.setFont(font);
            model_tab2.setFont(font);
            price2.setFont(font);
            price_tab2.setFont(font);
            warning2.setFont(font);
            spinDay2.setFont(font);

            left.setFont(font);
            save_edit.setFont(font);
            delete_tab2.setFont(font);
            refresh.setFont(font);
            right.setFont(font);
            edit_button.setFont(font);
            brands_tab2.setFont(font);
            models_tab2.setFont(font);
        /////////////////////////////

        tab2.add(note);
        tab2.add(brand);
        tab2.add(brand_tab2);
        tab2.add(model);
        tab2.add(model_tab2);
        tab2.add(price2);
        tab2.add(price_tab2);
        tab2.add(warning2);
        tab2.add(spinDay2);


        tab2.add(left);
        tab2.add(right);
        tab2.add(refresh);
        tab2.add(save_edit);
        tab2.add(delete_tab2);


        tab2.add(edit_button);
        tab2.add(brands_tab2);
        tab2.add(models_tab2);



        ///////////////////////////Tab3////////////////////
        JButton search = new JButton("Пошук");
        JTextArea result_tab3 = new JTextArea(12,37);
        result_tab3.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(result_tab3);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JButton models_in_brands = new JButton("Кількість моделей у кожної марки ");

        JTextArea model_in_brand_area = new JTextArea(5,30);
        model_in_brand_area.setEditable(false);

        JScrollPane scrollPane1 = new JScrollPane(model_in_brand_area);
        scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int price_tab3 = 0;
                result_tab3.setText(null);
                result_tab3.append("Результат:\n");
                Date date_search ;
                Auto auto_tab3 = new Auto();
                date_search = (Date) spinDay3.getValue();
                ArrayList<Auto> auto_search;
                auto_search = Auto.SearchByDate(date_search);

                for (int i = 0; i<auto_search.size();i++){
                    auto_tab3.SetBrand(auto_search.get(i).GetBrand());
                    auto_tab3.SetModel(auto_search.get(i).GetModel());
                    auto_tab3.SetPrice(auto_search.get(i).GetPrice());
                    auto_tab3.SetDate(auto_search.get(i).GetDate());
                    price_tab3 += Integer.parseInt(auto_tab3.GetPrice());

                    result_tab3.append("Марка:  "+auto_tab3.GetBrand()+"  Модель:  "+
                           auto_tab3.GetModel()+"  Ціна:  "+auto_tab3.GetPrice()+" $\n");
                }
                result_tab3.append("Сума : " + price_tab3+" $ ");
            }
        });


        models_in_brands.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Auto.Get_Size_ArrList_Auto()!=0){
                    model_in_brand_area.setText(Auto.Models_in_Brands());

                }
                else model_in_brand_area.setText("Помилка , немає автівок!");
            }
        });

    //////////Tab 3 set fonts//////////
        spinDay3.setFont(font);
        search.setFont(font);
        result_tab3.setFont(font);
        models_in_brands.setFont(font);
        model_in_brand_area.setFont(font);

    /////////////////////////////////

        tab3.add(spinDay3);
        tab3.add(search);
        tab3.add(scrollPane);
        tab3.add(models_in_brands);
        tab3.add(scrollPane1);
    //////////////////////////////////////////////////

        tab.addTab("Додавання", tab1);
        tab.addTab("Виведення/Редагування/Видалення", tab2);
        tab.addTab("Завдання",tab3);

        tab.setEnabledAt(1,false);
        tab.setEnabledAt(2,false);
        tab.setFont(font);

        getContentPane().add(tab);
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        String[] acura = {"ILX","MDX","RDX","TLX"};
        String[] audi = {"A2","A5","RS7","Q5","Q7","Q8","e-tron","TT"};
        String[] bmw = {"M2","M3","M4","M5","M7","M8","i3","i8","X1","X3","X4","X5","X6","X7","Z3","Z4"};
        String[] mercedes = {"A-Class","B-Class","C-Class","E-Class","V-Class","S-Class","EQA"
                ,"EQB","EQC","EQE","EQV","EQS","CLA","CLS","GLA","GLB","GLC","GLS","GLE",
                "GLC AMG","GLC AMG COUPE","Maybach"};


        if (e.getSource() == brands){
            if (brands.getSelectedItem().equals("Обрати")){
                save.setEnabled(false);
                models.setEnabled(false);
                price.setEditable(false);
            }

            else if (brands.getSelectedItem().equals("Acura")){
                models.setEnabled(true);
                save.setEnabled(true);
                price.setEditable(true);
                models.removeAllItems();
                for (int i = 0; i < acura.length;i++){
                    models.addItem(acura[i]);
                }
            }

            else if (brands.getSelectedItem().equals("Audi")){
                models.setEnabled(true);
                save.setEnabled(true);
                price.setEditable(true);
                models.removeAllItems();
                for (int i = 0; i < audi.length;i++){
                    models.addItem(audi[i]);
                }
            }

            else if (brands.getSelectedItem().equals("BMW")){
                models.setEnabled(true);
                save.setEnabled(true);
                price.setEditable(true);
                models.removeAllItems();
                for (int i = 0; i < bmw.length; i ++){
                    models.addItem(bmw[i]);
                }
            }else if (brands.getSelectedItem().equals("Mercedes")){
                models.setEnabled(true);
                save.setEnabled(true);
                price.setEditable(true);
                models.removeAllItems();
                for (int i = 0; i< mercedes.length; i ++){
                    models.addItem(mercedes[i]);
                }
            }
        }

        models.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                _model_ = (String) models.getSelectedItem();
            }
        });

        brands.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _brand_ = (String) brands.getSelectedItem();
            }
        });
    }


}
