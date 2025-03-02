package com.tagtraum.perf.gcviewer.view;

import com.tagtraum.perf.gcviewer.util.BuildInfoReader;
import com.tagtraum.perf.gcviewer.util.LocalisationHelper;
import com.tagtraum.perf.gcviewer.view.util.ImageHelper;
import com.tagtraum.perf.gcviewer.view.util.UrlDisplayHelper;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * About dialog showing version and contributors information.
 *
 * @author Hendrik Schreiber
 * @author <a href="mailto:gcviewer@gmx.ch">Joerg Wuethrich</a>
 */
public class AboutDialog extends ScreenCenteredDialog implements ActionListener {

    private static final String GCVIEWER_HOMEPAGE = "https://github.com/chewiebug/gcviewer/wiki";
    private static final String ACTION_HOMEPAGE = "homepage";

    private static final String[] CONTRIBUTORS = {
        "Hans Bausewein",
        "Peter Bilstein",
        "Steve Boardwell",
        "Krzysztof Burek",
        "Cka3o4Huk",
        "Frank Dietrich",
        "Bernd Eckenfels",
        "Matt Foulks",
        "Ryan Gardner",
        "Martin Geldmacher",
        "Neil Gentleman",
        "Chris Grindstaff",
        "Michi Gysel",
        "Roland Illig",
        "Mary Sunitha Joseph",
        "Johan Kaving",
        "Nikolay Khramchenkov",
        "Maciej Kwiecien",
        "Dennis Lawler",
        "Yanqi Li",
        "Henry Lin",
        "James Livingston",
        "Mart Mägi",
        "Tony Mancill",
        "Auston McReynolds",
        "Samuel Mendenhall",
        "Carl Meyer",
        "Ondrej Mihályi",
        "Reinhard Nägele",
        "Robert Nibali",
        "Àngel Ollé Blázquez",
        "Thomas Peyrard",
        "Rupesh Ramachandran",
        "Fred Rolland",
        "Sandro Rossi",
        "Heiko W. Rupp",
        "Stephan Schroevers",
        "François Secherre",
        "Serafín Sedano",
        "Jugal Shah",
        "Andrey Skripalschikov",
        "Jeffrey Swan",
        "Kamil Szymanski",
        "Pierre Viret",
        "Cui Weiloong",
        "Yin Xunjun",
        "Leslie Zhai",
        "Eugene Zimichev"};

    public AboutDialog(Frame f) {
        super(f, LocalisationHelper.getString("about_dialog_title"));
        Panel logoPanel = new Panel();
        ImageIcon logoIcon = ImageHelper.loadImageIcon(LocalisationHelper.getString("about_dialog_image"));
        JLabel la_icon = new JLabel(logoIcon);
        la_icon.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        logoPanel.add(la_icon);

        JPanel versionPanel = new JPanel();
        versionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        versionPanel.setLayout(new GridBagLayout());

        JLabel copyright = new JLabel("\u00A9" + " 2011-2022: Joerg Wuethrich and contributors", JLabel.CENTER);

        JLabel contributorsLabel = new JLabel("contributors (alphabetically ordered):", JLabel.CENTER);
        contributorsLabel.setForeground(Color.GRAY);
        JLabel contributors = new JLabel(formatContributors(CONTRIBUTORS), JLabel.CENTER);
        contributors.setPreferredSize(calculatePreferredSize(contributors, true, logoIcon.getIconWidth()));

        JLabel version = new JLabel("<html><font color=\"gray\">version:</font> " + BuildInfoReader.getVersion() + "</html>", JLabel.CENTER);
        JLabel buildDate = new JLabel("<html><font color=\"gray\">build date:</font> " + BuildInfoReader.getBuildDate() + "</html>", JLabel.CENTER);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.gridx = 0;

        versionPanel.add(copyright, gridBagConstraints);

        Insets insetsGapOnTop = new Insets(10, 0, 0, 0);
        GridBagConstraints gridBagConstraintsGapOnTop = new GridBagConstraints();
        gridBagConstraintsGapOnTop.gridy = 1;
        gridBagConstraintsGapOnTop.insets = insetsGapOnTop;
        versionPanel.add(contributorsLabel, gridBagConstraintsGapOnTop);

        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        versionPanel.add(contributors, gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;

        gridBagConstraintsGapOnTop.gridy = 3;
        versionPanel.add(version, gridBagConstraintsGapOnTop);

        gridBagConstraints.gridy = 4;
        versionPanel.add(buildDate, gridBagConstraints);

        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        if (UrlDisplayHelper.displayUrlIsSupported()) {
            JButton homePageButton = new JButton(LocalisationHelper.getString("button_homepage"));
            homePageButton.setActionCommand(ACTION_HOMEPAGE);
            homePageButton.addActionListener(this);
            buttonPanel.add(homePageButton);
        }

        JButton okButton = new JButton(LocalisationHelper.getString("button_ok"));
        okButton.setActionCommand(ACTION_OK);
        okButton.addActionListener(this);
        buttonPanel.add(okButton);
        getContentPane().add("North", logoPanel);
        getContentPane().add("Center", versionPanel);
        getContentPane().add("South", buttonPanel);
        pack();
        setResizable(false);
        setVisible(false);
    }

    private String formatContributors(String[] contributors) {
        StringBuilder sb = new StringBuilder("<html><center>");
        for (String contributor : contributors) {
            sb.append(contributor).append(" | ");
        }

        sb.delete(sb.length() - 3, sb.length());

        sb.append("</center></html>");

        return sb.toString();
    }

    /**
     * Returns the preferred size to set a component at in order to render
     * an html string.  You can specify the size of one dimension.
     *
     * @see <a href="http://blog.nobel-joergensen.com/2009/01/18/changing-preferred-size-of-a-html-jlabel/">reference for this implementation</a>
     */
    private Dimension calculatePreferredSize(JLabel labelWithHtmlText, boolean width, int preferredSize) {

        View view = (View) labelWithHtmlText.getClientProperty(BasicHTML.propertyKey);

        view.setSize(width ? preferredSize : 0,
                     width ? 0 : preferredSize);

        float w = view.getPreferredSpan(View.X_AXIS);
        float h = view.getPreferredSpan(View.Y_AXIS) * (float)1.1; // add 10% to compensate for high dpi screens with jdk 11

        return new Dimension((int) Math.ceil(w),
                (int) Math.ceil(h));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ACTION_HOMEPAGE.equals(e.getActionCommand())) {
            UrlDisplayHelper.displayUrl(this, GCVIEWER_HOMEPAGE);
        }
        else {
            // default action
            super.actionPerformed(e);
        }
    }

}
