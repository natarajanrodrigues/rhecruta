/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.jse.frames;

import br.edu.ifpb.dac.rhecruta.jse.ServiceLocator;
import br.edu.ifpb.dac.rhecruta.shared.domain.dto.Curriculum;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.SystemEvaluation;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.CurriculumService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EnterviewService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.SystemEvaluationService;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 */
public class InterviewEvalueFrame extends javax.swing.JFrame {

    private static final String ENTERVIEW_RESOURCE = "java:global/rhecruta-core/EnterviewServiceImpl!br.edu.ifpb.dac.rhecruta.shared.interfaces.EnterviewService";
    private static final String CURRICULUM_RESOURCE = "java:global/rhecruta-core/CurriculumServiceImpl!br.edu.ifpb.dac.rhecruta.shared.interfaces.CurriculumService";
    private static final String SYSTEM_EVALUATION_RESOURCE = "java:global/rhecruta-core/SystemEvaluationServiceImpl!br.edu.ifpb.dac.rhecruta.shared.interfaces.SystemEvaluationService";
    private DecimalFormat decimalFormat = new DecimalFormat("##.00");
    private SystemEvaluation evaluation;

    private ServiceLocator serviceLocator = new ServiceLocator();
    private CurriculumService curriculumService;
    private SystemEvaluationService systemEvaluationService;
    private EnterviewService enterviewService;
    private Enterview enterview;

    /**
     * Creates new form EnterviewEvalue
     */
    public InterviewEvalueFrame() {
        initComponents();
    }

    public InterviewEvalueFrame(Enterview enterview) {
        initComponents();
        setLocationRelativeTo(null);
        this.enterview = enterview;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Candidate: " + enterview.getCandidate().getFirstname() + " | " + enterview.getOffer().getDescription());
        appraiseSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 10.0, 0.1));

        nameLabel.setText(enterview.getCandidate().getFirstname());
        lastNameLabel.setText(enterview.getCandidate().getLastname());
        emailLabel.setText(enterview.getCandidate().getUser().getCredentials().getEmail());
        requestSkillsTextArea.setText(enterview.getOffer().getSkills().toString());

        if (enterview.getAddress() != null) {
            realStreetLabel.setText(enterview.getAddress().getStreet());
            cityLabel.setText(enterview.getAddress().getCity());
            stateLabel.setText(enterview.getAddress().getState());
            numberLabel.setText(enterview.getAddress().getNumber());
            countryLabel.setText(enterview.getAddress().getCountry());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        dateTimeStartLabel.setText(enterview.getStart().format(formatter));
        dateTimeEndLabel.setText(enterview.getEnd().format(formatter));

        offerNameLabel.setText(enterview.getOffer().getDescription());
        offerVacancyLabel.setText(enterview.getOffer().getCandidates().size() + "/" + enterview.getOffer().getVacancies());

        if (enterview.getEnd().isAfter(LocalDateTime.now())) {
            appraiseSpinner.setEnabled(false);
            appraiseSpinner.setFocusable(false);
            evalueButton.setEnabled(false);
        }

        systemEvaluationService = serviceLocator
                .lookup(SYSTEM_EVALUATION_RESOURCE, SystemEvaluationService.class);

        evaluation = systemEvaluationService.getByOfferAndCandidate(enterview.getCandidate(), enterview.getOffer());

        if (evaluation.getScore() != null) {
            rankingLabel.setText(decimalFormat.format(evaluation.getScore() * 100) + "%");
        }

        if (enterview.getScore() != null) {
            appraiseSpinner.setValue(new Double(enterview.getScore() * 10));
        }

        if (evaluation.getScore() != null && enterview.getScore() != null) {
            double fs = ((evaluation.getScore() * 100) / 2) + ((enterview.getScore() * 100) / 2);
            fsLabel.setText(decimalFormat.format(fs) + "%");
        }

        curriculumService = serviceLocator.lookup(CURRICULUM_RESOURCE, CurriculumService.class);
        if (!curriculumService.hasCurriculum(enterview.getCandidate().getId())) {
            downloadCurriculumButton.setEnabled(false);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel24 = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        numberLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        skillsTextArea = new javax.swing.JTextArea();
        countryLabel = new javax.swing.JLabel();
        offerVacancyLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        offerNameLabel = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        downloadCurriculumButton = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        rankingLabel = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        requestSkillsTextArea = new javax.swing.JTextArea();
        cityLabel = new javax.swing.JLabel();
        stateLabel = new javax.swing.JLabel();
        streetLabel = new javax.swing.JLabel();
        dateTimeStartLabel = new javax.swing.JLabel();
        dateTimeEndLabel = new javax.swing.JLabel();
        evalueButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        appraiseSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        fsLabel = new javax.swing.JLabel();
        realStreetLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel24.setText("Number:");

        skillsTextArea.setEditable(false);
        skillsTextArea.setColumns(20);
        skillsTextArea.setRows(5);
        skillsTextArea.setFocusable(false);
        jScrollPane3.setViewportView(skillsTextArea);

        jLabel9.setText("Name:");

        jLabel12.setText("Vacancy:");

        jLabel10.setText("#Candidate");

        jLabel11.setText("E-mail:");

        jLabel14.setText("Name:");

        jLabel13.setText("#Offer infos:");

        jLabel1.setText("Interview:");

        jLabel3.setText("#Evaluations:");

        jLabel4.setText("System:");

        jLabel15.setText("#Interview infos:");

        jLabel16.setText("Date/Time Start:");

        jLabel17.setText("Date/Time End:");

        jLabel19.setText("Street:");

        downloadCurriculumButton.setText("Download Curriculum");
        downloadCurriculumButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadCurriculumButtonActionPerformed(evt);
            }
        });

        jLabel20.setText("City:");

        rankingLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rankingLabel.setText("0.0");
        rankingLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel21.setText("State:");

        jLabel22.setText("Country:");

        jLabel7.setText("#Skills");

        jLabel8.setText("#Skills Request");

        requestSkillsTextArea.setEditable(false);
        requestSkillsTextArea.setColumns(20);
        requestSkillsTextArea.setRows(5);
        requestSkillsTextArea.setFocusable(false);
        jScrollPane1.setViewportView(requestSkillsTextArea);

        dateTimeStartLabel.setText("22/22/2222 22:22");

        dateTimeEndLabel.setText("jLabel6");

        evalueButton.setText("Evalue");
        evalueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                evalueButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Last Name:");

        jLabel2.setText("Final Score:");

        fsLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        fsLabel.setText("0.0");
        fsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(downloadCurriculumButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lastNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(rankingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(appraiseSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(evalueButton, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(offerVacancyLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel13)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(offerNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateTimeEndLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cityLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateTimeStartLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(countryLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(streetLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(realStreetLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel15))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel8)
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9))
                            .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel19)
                                .addComponent(realStreetLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(lastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(emailLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(downloadCurriculumButton, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel20)
                                        .addComponent(cityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(stateLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel21))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(numberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel22)
                                        .addComponent(countryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel16)
                                        .addComponent(dateTimeStartLabel)))
                                .addComponent(streetLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(evalueButton)
                                    .addComponent(appraiseSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(rankingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(fsLabel)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(dateTimeEndLabel))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14)
                                    .addComponent(offerNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel12)
                                    .addComponent(offerVacancyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void downloadCurriculumButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadCurriculumButtonActionPerformed
        downloadCurriculumButton.setEnabled(false);
        downloadCurriculumButton.setFocusable(false);
        
        JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        chooser.showOpenDialog(null);
//        chooser.setVisible(true);

        int status = chooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            downloadCurriculumButton.setText("Downloading File ...");
            String path = chooser.getSelectedFile().getAbsolutePath();
            if (curriculumService == null) {
                curriculumService = serviceLocator.lookup(CURRICULUM_RESOURCE, CurriculumService.class);
            }
            if (curriculumService != null) {
                Curriculum curriculum = curriculumService.get(enterview.getCandidate().getId());
                if (curriculum != null) {
                    File file = new File(path + File.separator + curriculum.getFilename());
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(curriculum.getBytes());
                        JOptionPane.showMessageDialog(null, "Curriculum save with sucess!");
                    } catch (IOException ex) {
                        Logger.getLogger(InterviewEvalueFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        downloadCurriculumButton.setEnabled(true);
        downloadCurriculumButton.setFocusable(true);
        downloadCurriculumButton.setText("Download Curriculum");
    }//GEN-LAST:event_downloadCurriculumButtonActionPerformed

    private void evalueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evalueButtonActionPerformed

        enterviewService = serviceLocator.lookup(ENTERVIEW_RESOURCE, EnterviewService.class);
        try {
            appraiseSpinner.commitEdit();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        Double value = (double) appraiseSpinner.getValue();

        if (value != null) {
            enterview.setScore(value / 10);
        }

        if (evaluation.getScore() != null && enterview.getScore() != null) {
            double fs = ((evaluation.getScore() * 100) / 2) + ((enterview.getScore() * 100) / 2);
            fsLabel.setText(decimalFormat.format(fs) + "%");
        }

        try {
            enterviewService.evaluate(enterview, value / 10);
            JOptionPane.showMessageDialog(null, "Interview Enterview Score Up");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Invelid number format");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }//GEN-LAST:event_evalueButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterviewEvalueFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterviewEvalueFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterviewEvalueFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterviewEvalueFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterviewEvalueFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner appraiseSpinner;
    private javax.swing.JLabel cityLabel;
    private javax.swing.JLabel countryLabel;
    private javax.swing.JLabel dateTimeEndLabel;
    private javax.swing.JLabel dateTimeStartLabel;
    private javax.swing.JButton downloadCurriculumButton;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JButton evalueButton;
    private javax.swing.JLabel fsLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel numberLabel;
    private javax.swing.JLabel offerNameLabel;
    private javax.swing.JLabel offerVacancyLabel;
    private javax.swing.JLabel rankingLabel;
    private javax.swing.JLabel realStreetLabel;
    private javax.swing.JTextArea requestSkillsTextArea;
    private javax.swing.JTextArea skillsTextArea;
    private javax.swing.JLabel stateLabel;
    private javax.swing.JLabel streetLabel;
    // End of variables declaration//GEN-END:variables
}
