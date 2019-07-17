/**
 * Control Panel v.2.0
 *
 * Created by: Jeffrey Hejna
 * Last Updated: 3/28/16
 * ---------------------------------------------------------
 *
 * This is control panel for a room in which the user can
 * turn on/off lights, raise/lower shades, lock/unlock doors,
 * and change the temperature.
 *
 * ----------------------------------------------------------
 */


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;


public class Control_Panel extends JFrame{

    private JToggleButton[] lights, doors, windows;
    private JSlider dimmer;
    private JSlider[] shades, lightSliders;
    private JToggleButton allLights, allDoors, allShades;
    private ImageIcon lightbulbOFF, lightbulbOn, lock, unlock, window;
    private JSpinner tempControl;


    public Control_Panel(){


        //creating the icons for the buttons in order to
        //provide visual feedback for the user
        lightbulbOFF = new ImageIcon("src/LightBulbOff.jpg");
        lightbulbOn = new ImageIcon("src/LightBulbOn.jpg");
        lock = new ImageIcon("src/Lock.png");
        unlock = new ImageIcon("src/Unlock.png");
        window = new ImageIcon("src/Window.jpg");







        //Implementing the Temperature Control
        tempControl = new JSpinner(new SpinnerNumberModel(60,40,90,1));     // Used a range from 40-90 because I don't imagine
                                                                            // someone setting the temperature higher or lower
                                                                            // than these values.
        ((JSpinner.DefaultEditor)tempControl.getEditor()).getTextField().setEditable(false);



        //Implementing the light dimmer
        dimmer= new JSlider(JSlider.HORIZONTAL,0,10,0);              // Dimmer is a slider which allows the user to adjust the
        dimmer.setEnabled(false);                                    // brightness with ease. Tick spaces are added to give the
        dimmer.setMajorTickSpacing(5);                              // user a sense of how bright the light should be knowing
        dimmer.setMinorTickSpacing(1);                               // that the end is max brightness. The label 'Dim' was necessary
        dimmer.setPaintTicks(true);                                  // so that the user knows it is not just 'Off'. These labels are
        Hashtable dimerLabel = new Hashtable();                      // self explanatory.
        dimerLabel.put(0, new JLabel("Dim"));
        dimerLabel.put(10, new JLabel("Max"));                       // The dimmer is right in the center of the six lights, thereby
        dimmer.setLabelTable(dimerLabel);                            // grouping these objects together.
        dimmer.setPaintLabels(true);

        dimmer.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (dimmer.getValueIsAdjusting()){
                    int position = dimmer.getValue();
                    for (int i = 0; i < 6; i++) {
                        lightSliders[i].setValue(position);
                    }
                }
            }
        });


        //Initializing shades, lights, doors, and windows
        lights = new JToggleButton[6];                          // arrays were an easier choice to loop through every element.
        doors = new JToggleButton[2];
        windows = new JToggleButton[4];                         // was easier to link each window and shade together this way.
        shades= new JSlider[4];
        lightSliders = new JSlider[6];




        //Implementing allLights button
        allLights = new JToggleButton("Turn All Lights On");            // Toggles all the lights to be On/Off
        allLights.setPreferredSize(new Dimension(90, 40));              // The light icons go from a dull bulb to
        allLights.addActionListener(new ActionListener() {              // one that is lit up to give the user feedback
            @Override                                                   // as to which lights are on.
            public void actionPerformed(ActionEvent e) {
                if (allLights.isSelected()) {
                    allLights.setText("Turn All Lights Off");
                    for (int i = 0; i < 6; i++) {
                        lights[i].setSelected(true);
                        lights[i].setIcon(lightbulbOn);
                        lightSliders[i].setEnabled(true);
                        dimmer.setEnabled(true);
                    }
                } else {
                    allLights.setText("Turn All Lights On");
                    for (int i = 0; i < 6; i++) {
                        lights[i].setSelected(false);
                        lights[i].setIcon(lightbulbOFF);
                        lightSliders[i].setEnabled(false);
                        dimmer.setEnabled(false);
                    }
                }
            }
        });




        //Implementing allDoors button
        allDoors = new JToggleButton("Door Locks");
        allDoors.setPreferredSize(new Dimension(100, 40));           // Toggles both doors to be locked/unlocked
        allDoors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (allDoors.isSelected()) {
                    doors[0].setSelected(true);
                    doors[0].setIcon(unlock);
                    doors[1].setSelected(true);
                    doors[1].setIcon(unlock);
                } else {
                    doors[0].setSelected(false);
                    doors[0].setIcon(lock);
                    doors[1].setSelected(false);
                    doors[1].setIcon(lock);
                }
                for (int j = 0; j < 2; j++) {
                    if (doors[j].isSelected()){
                        allDoors.setSelected(true);

                    }
                    else{
                        allDoors.setSelected(false);
                        break;
                    }
                }
            }
        });



        //Implementing individual lights
        for (int i = 0; i < 6; i++) {
            lights[i]=new JToggleButton(lightbulbOFF);

            lights[i].setMaximumSize(new Dimension(70, 70));
            lightSliders[i] = new JSlider(JSlider.VERTICAL,0,10,0);
            lightSliders[i].setMaximumSize(new Dimension(50, 100));
            lightSliders[i].setMajorTickSpacing(5);                               // user a sense of how bright the light should be knowing
            lightSliders[i].setMinorTickSpacing(1);                               // that the end is max brightness. The label 'Dim' was necessary
            lightSliders[i].setPaintTicks(true);                                  // so that the user knows it is not just 'Off'. These labels are
            Hashtable lightLabel = new Hashtable();                               // self explanatory.
            lightLabel.put(0, new JLabel("Dim"));
            lightLabel.put(10, new JLabel("Max"));                                // The dimmer is right in the center of the six lights, thereby
            lightSliders[i].setLabelTable(lightLabel);                            // grouping these objects together.
            lightSliders[i].setPaintLabels(true);
            lightSliders[i].setEnabled(false);

            int x = i;

            lights[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (lights[x].isSelected()) {                   // toggles the icon shown which provides visual
                        lights[x].setIcon(lightbulbOn);             // feedback to the user.
                        allLights.setSelected(false);
                        lightSliders[x].setEnabled(true);


                    } else {
                        lights[x].setIcon(lightbulbOFF);
                        lightSliders[x].setEnabled(false);
                    }

                    for (int j = 0; j < 6; j++) {
                        if (lights[j].isSelected()) {                   // if none of the lights are on, the dimmer is
                            dimmer.setEnabled(true);                    // disabled, providing a constraint so that the
                            allLights.setText("Turn All Lights Off");   // user cannot use it when there would be no point
                        } else {                                        // in using it.
                            dimmer.setEnabled(false);
                            allLights.setSelected(false);
                            allLights.setText("Turn All Lights On");
                            break;
                        }
                    }

                    if (lights[0].isSelected() && lights[1].isSelected() && lights[2].isSelected() && lights[3].isSelected() && lights[4].isSelected() && lights[5].isSelected()) {
                        allLights.setSelected(true);
                    } else {
                        allLights.setSelected(false);
                    }

                }
            });
            

        }



        //Implementing the shades
        allShades = new JToggleButton("Shades Up");
        allShades.setPreferredSize(new Dimension(100,40));          // Puts the shades sliders all the way up/down
        allShades.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (allShades.isSelected()) {
                    allShades.setText("Shades Down");
                    for (int i = 0; i < 4; i++) {
                        shades[i].setValue(10);
                    }
                }else{
                    allShades.setText("Shades Up");
                    for (int i = 0; i < 4; i++) {
                        shades[i].setValue(0);
                    }
                }
            }
        });





        //Implementing the windows
        for (int i = 0; i < 4; i++) {
            windows[i] = new JToggleButton(window);                 // creating the windows toggle buttons
            windows[i].setMaximumSize(new Dimension(60, 40));       // these buttons have lock/unlock icons present
            windows[i].setSelected(false);                          // depending on whether it is selected or not.
            shades[i]= new JSlider(JSlider.VERTICAL,0,10,0);
                                                                    // made the shades sliders in that it is easy for the user
            int x=i;                                                // to move them up and down, like the actual shades.
            windows[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {        // only makes the shades sliders enabled if the specific
                    if (windows[x].isSelected()) {                  // window is selected. This provides a constraint so that the
                        shades[x].setValue(10);                     // user cannot accidentally change the shades unless they specifically
                    } else {                                        // choose the window first.
                        shades[x].setValue(0);
                    }
                }
            });

            shades[i].addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if (shades[x].getValue() == 10) {
                        windows[x].setSelected(true);
                    } else {
                        windows[x].setSelected(false);
                    }
                    for (int j = 0; j < 4; j++) {
                        if(shades[j].getValue()==10){
                            allShades.setSelected(true);
                            allShades.setText("Shades Down");
                        }
                        else{
                            allShades.setSelected(false);
                            allShades.setText("Shades Up");
                            break;
                        }
                    }
                }
            });

        }




        //Implementing the doors
        for (int i = 0; i < 2; i++) {
            doors[i]= new JToggleButton(lock);                        // doors are given icons that show a lock that is locked and unlocked.
            doors[i].setPreferredSize(new Dimension(10, 100));        // This provides valuable visual feedback to the user in that these icons
            int x = i;                                                // are self explanatory.
            doors[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (doors[x].isSelected()) {
                        doors[x].setIcon(unlock);
                    } else {
                        doors[x].setIcon(lock);
                    }
                    for (int j = 0; j < 2; j++) {
                        if (doors[j].isSelected()){
                            allDoors.setSelected(true);
                        }
                        else{
                            allDoors.setSelected(false);
                            break;
                        }
                    }
                }
            });

        }









        //The mainframe for the GUI
        Container mainframe = getContentPane();             //Border Layout allowed for a good separation of groups:
        mainframe.setLayout(new BorderLayout());
        mainframe.add(topLayout(), BorderLayout.NORTH);     // Temp Control,
        mainframe.add(leftLayout(),BorderLayout.WEST);      // Windows, shades, and door
        mainframe.add(centerLayout(),BorderLayout.CENTER);  // Lights, Dimmer
        mainframe.add(rightLayout(),BorderLayout.EAST);     // Windows, shades, and door
        mainframe.add(bottomLayout(),BorderLayout.SOUTH);   // All doors, all lights, all shades

    }


    protected JComponent topLayout(){
        JPanel top = new JPanel();
        top.setLayout(new FlowLayout());
        top.setBackground(new Color(0,98,139));
        top.add(new JLabel("CONTROL PANEL v.2.0                                  Temp (°F):"));        // FlowLayout was used to keep label, and tempControl together
                                                                                                       // in close proximity, thereby insuring association.
        top.add(tempControl);
        JTextArea area = new JTextArea("** You Are Here **");
        area.setForeground(Color.red);
        top.add(new JLabel("                                            "));        // used to make sure everything is aligned in the center
        top.add(area);
        top.add(Box.createRigidArea(new Dimension(1, 70)));

        return top;
    }

    protected JComponent leftLayout(){
        JPanel left = new JPanel();                         // Allows for the proximity of the windows and shades, thereby grouping them together
        left.setBackground(new Color(129,165,148));
        left.setLayout(new BorderLayout());
        left.add(new JLabel("   "),BorderLayout.WEST);      // Providing some space from the edge of the GUI
        left.add(leftLeftLayout(), BorderLayout.CENTER);    // The windows and door
        left.add(leftCenter(),BorderLayout.EAST);           // shades

        return left;
    }
    protected JComponent leftLeftLayout(){
        JPanel leftleft= new JPanel();
        leftleft.setBackground(new Color(129,165,148));
        leftleft.setLayout(new GridLayout(3, 1, 0, 80));    // GridLayout to provide even spacing between door and windows
        leftleft.add(windows[0]);
        leftleft.add(doors[0]);
        leftleft.add(windows[1]);

        return leftleft;
    }

    protected JComponent leftCenter(){
        JPanel leftCenter = new JPanel();
        leftCenter.setBackground(new Color(129,165,148));
        leftCenter.setLayout(new GridLayout(2, 1, 0, 270));   // GridLayout to provide spacing between the two shade slider and so they line
        leftCenter.add(shades[0]);                            // up next to each window.
        leftCenter.add(shades[1]);

        return leftCenter;

    }
    protected JComponent rightCenter(){
        JPanel rightCenter = new JPanel();
        rightCenter.setBackground(new Color(129,165,148));
        rightCenter.setLayout(new GridLayout(2, 1, 0, 270));    // same strategy applied from leftCenter() but for the right
        rightCenter.add(shades[2]);
        rightCenter.add(shades[3]);

        return rightCenter;

    }

    protected JComponent rightRightLayout(){
        JPanel right= new JPanel();
        right.setBackground(new Color(129,165,148));
        right.setLayout(new GridLayout(3, 1, 0, 80));       // same strategy applied from leftCenter() but for the right

        right.add(windows[2]);

        right.add(doors[1]);

        right.add(windows[3]);
        return right;
    }



    protected JComponent rightLayout(){
        JPanel right = new JPanel();
        right.setBackground(new Color(129,165,148));
        right.setLayout(new BorderLayout());
        right.add(new JLabel("   "),BorderLayout.EAST);         //Providing space between edge of GUI and buttons
        right.add(rightRightLayout(), BorderLayout.CENTER);     // Windows, door
        right.add(rightCenter(),BorderLayout.WEST);             // shades
        return right;
    }

    protected JComponent bottomLayout(){
        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(129, 165, 148));
        bottom.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));     // FlowLayout of the bottom layer of buttons
        //bottom.add(Box.createRigidArea(new Dimension(5, 30)));        // structured so they are evenly space and
        bottom.add(new JLabel("Master Controls:"));                     // centered.
        bottom.add(allDoors);
        //bottom.add(Box.createRigidArea(new Dimension(5, 5)));
        bottom.add(allShades);
        bottom.add(new JLabel("                 "));
        bottom.add(Box.createRigidArea(new Dimension(1, 70)));
        return bottom;
    }

    protected JComponent centerLayout(){
        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());                   // Making the center of the main layout an additional BorderLayout
        center.add(centerLayoutTop(), BorderLayout.NORTH);      // so that the lights can be on top and bottom of this layout
        center.add(centerLayoutBottom(),BorderLayout.SOUTH);    // and the middle of this layout will be the dimmer
        center.add(centercenter(), BorderLayout.CENTER);

        return center;
    }

    protected JComponent centercenter(){
        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());
        center.setBackground(new Color(230,230,220));
        center.add(centercenterTop(), BorderLayout.NORTH);

        center.add(dimmer,BorderLayout.CENTER);
        center.add(new JLabel("                                                  "),BorderLayout.EAST);
        center.add(new JLabel("                                                  "),BorderLayout.WEST);

        return center;
    }
    protected JComponent centercenterTop(){
        JPanel centercentertop = new JPanel();
        centercentertop.setBackground(new Color(230,230,220));
        centercentertop.setLayout(new GridLayout(1, 3));
        centercentertop.add(new JLabel("                                 "));
        centercentertop.add(allLights);
        centercentertop.add(new JLabel("                                 "));
        return centercentertop;

    }

    protected JComponent centerLayoutTop(){
        JPanel centerTop = new JPanel();
        centerTop.setLayout(new BoxLayout(centerTop, BoxLayout.X_AXIS));   
        centerTop.add(Box.createRigidArea(new Dimension(80, 160)));
        centerTop.setBackground(new Color(230,230,220));
        for (int i = 0; i < 3; i++) {
            centerTop.add(lights[i]);
            centerTop.add(Box.createRigidArea(new Dimension(3, 1)));
            centerTop.add(lightSliders[i]);
            centerTop.add(Box.createRigidArea(new Dimension(100, 1)));
        }
        return centerTop;
    }


    protected JComponent centerLayoutBottom(){
        JPanel centerBottom = new JPanel();
        centerBottom.setLayout(new BoxLayout(centerBottom,BoxLayout.X_AXIS));   // same logic applied from the comments above.
        centerBottom.add(Box.createRigidArea(new Dimension(80, 160)));
        centerBottom.setBackground(new Color(230,230,220));
        for (int i = 3; i < 6; i++) {
            centerBottom.add(lights[i]);
            centerBottom.add(Box.createRigidArea(new Dimension(3,1)));
            centerBottom.add(lightSliders[i]);
            centerBottom.add(Box.createRigidArea(new Dimension(100,1)));
        }
        return centerBottom;
    }


    public static void main(String[] args) {
        Control_Panel c = new Control_Panel();
        c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c.setSize(900, 650);
        c.setResizable(false);
        c.setVisible(true);

    }




}
