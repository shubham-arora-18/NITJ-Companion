package com.warkahot.nitjcompanion;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by warkahot on 22-Sep-16.
 *
 *
 */

public class support extends AppCompatActivity {


    Toolbar toolbar;
    TextView my_info;
    ImageView my_image;
    EditText suggestion;
    Button send_button;
    ProgressDialog pd;
    int val1 = 0;
    int val2 = 0;

    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.support);

        toolbar = (Toolbar) findViewById(R.id.events_toolbar);
        setSupportActionBar(toolbar);

        my_info = (TextView)findViewById(R.id.my_info);
        my_image = (ImageView)findViewById(R.id.my_pic);
        suggestion = (EditText)findViewById(R.id.suggestions);
        send_button = (Button)findViewById(R.id.send_button);
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//enable up button ot go the parent activity
        setTitle("Support");

        final DatabaseReference dr_sug = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/").child("Suggestions");
        final DatabaseReference dr_mi = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/").child("My_info");

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (suggestion.getText().toString().length() > 1) {
                    dr_sug.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    dr_sug.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    dr_sug.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("suggestion/report").setValue(suggestion.getText().toString());
                    dr_sug.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(getApplicationContext(), "Thank you !!! Your response has been recorded...", Toast.LENGTH_SHORT).show();
                                suggestion.setText("Thank you for your response !!! :)");
                            } else
                                Toast.makeText(getApplicationContext(), "Some error has occured please check again in some time...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Please type something in the typing space available", Toast.LENGTH_SHORT).show();
                }

            }
        });

        if(new Network_available().isNetworkAvailable(getApplicationContext())) {
            pd.show();
        dr_mi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pd.dismiss();
                if (dataSnapshot.exists()) {
                        my_info.setText(dataSnapshot.child("info").getValue(String.class));
                    Picasso.with(getApplicationContext()).load(dataSnapshot.child("image").getValue(String.class)).transform(new CircleTransform()).into(my_image);

                }
                if(dataSnapshot.child("val1").exists() && dataSnapshot.child("val2").exists())
                {
                    val1 = (dataSnapshot.child("val1").getValue(Integer.class));
                    val2 = (dataSnapshot.child("val2").getValue(Integer.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (pd.isShowing()) {
                        AppMsg amk = AppMsg.makeText(support.this, "An error occured. Please try again in some time...", AppMsg.STYLE_ALERT);
                        amk.show();
                        pd.dismiss();
                    }

                }
            }, 60000);

        }
        else
        {
            AppMsg appMsg = AppMsg.makeText(support.this,"Please check your Internet Connection",AppMsg.STYLE_ALERT);
            appMsg.show();
            pd.dismiss();
        }


        my_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               val1--;
                System.out.println(" value of val1 = " + val1);
            }
        });

        my_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(val1 == 0)
                {
                    val2--;
                    System.out.println(" value of val2 = "+val2);
                    if(val2 == 0)
                    {
                        DatabaseReference fv = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/My_info");
                        fv.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                my_info.setText(dataSnapshot.child("sinfo").getValue(String.class));
                                Picasso.with(getApplicationContext()).load(dataSnapshot.child("simage").getValue(String.class)).transform(new CircleTransform()).into(my_image);
                                nf3();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });

    }
    public void new_function()
    {
        List<add_society_1> l1 = new ArrayList<add_society_1>() ;
        /*l1.add(new add_society_1("Nitovators", "https://firebasestorage.googleapis.com/v0/b/nitj-companion-24386.appspot.com/o/technical%20societies%2Fnitovators.png?alt=media&token=e82e1505-3918-4810-8636-c64922010ad1"));
        l1.add(new add_society_1("OPENGEEST","https://firebasestorage.googleapis.com/v0/b/nitj-companion-24386.appspot.com/o/technical%20societies%2Fopengeest.png?alt=media&token=fa25d598-98f4-49ca-bbab-e70bd5958710"));
        l1.add(new add_society_1("CODE-IT", "https://firebasestorage.googleapis.com/v0/b/nitj-companion-24386.appspot.com/o/technical%20societies%2Fcode-it.png?alt=media&token=632256b3-ad6c-4ca2-a317-0b8015dcc728"));
        l1.add(new add_society_1("SOECE", "https://firebasestorage.googleapis.com/v0/b/nitj-companion-24386.appspot.com/o/technical%20societies%2Fsoece.png?alt=media&token=6669cb25-9c25-4667-a23f-7c97861f95f3"));
        l1.add(new add_society_1("SOCCER", "https://firebasestorage.googleapis.com/v0/b/nitj-companion-24386.appspot.com/o/technical%20societies%2Fsoccer.png?alt=media&token=e0bc7fd9-03a6-44a5-9137-3c5670b12b79"));
        l1.add(new add_society_1("CHESS","https://firebasestorage.googleapis.com/v0/b/nitj-companion-24386.appspot.com/o/technical%20societies%2Fchess.png?alt=media&token=a8851c4c-6946-4d00-b7f1-806668a08b1c"));
        l1.add(new add_society_1("SAE", "https://firebasestorage.googleapis.com/v0/b/nitj-companion-24386.appspot.com/o/technical%20societies%2Fsae.png?alt=media&token=0e4666b2-7a84-4dfd-86fd-82b12984bb7f"));
        l1.add(new add_society_1("SOBER","https://firebasestorage.googleapis.com/v0/b/nitj-companion-24386.appspot.com/o/technical%20societies%2Fsober.png?alt=media&token=c60d46c2-8393-464b-9a1a-79cbb2f33211"));
        l1.add(new add_society_1("SOME", "https://firebasestorage.googleapis.com/v0/b/nitj-companion-24386.appspot.com/o/technical%20societies%2Fsome.png?alt=media&token=11609479-d9e7-4b8f-8b41-3a707f250dee"));
        l1.add(new add_society_1("SOEE", "https://firebasestorage.googleapis.com/v0/b/nitj-companion-24386.appspot.com/o/technical%20societies%2Fsoee.png?alt=media&token=2147197d-465e-490a-9b17-2c356703e495"));
        l1.add(new add_society_1("SPICE", "https://firebasestorage.googleapis.com/v0/b/nitj-companion-24386.appspot.com/o/technical%20societies%2Fspice.png?alt=media&token=24ead0a2-f96a-4aef-8c34-290ed6f3c7b1"));
        l1.add(new add_society_1("TEX-STYLES","https://firebasestorage.googleapis.com/v0/b/nitj-companion-24386.appspot.com/o/technical%20societies%2Ftexstyles.png?alt=media&token=39255a81-c6d2-44cf-ac10-4e4c740199e8"));
        l1.add(new add_society_1("TIES", "https://firebasestorage.googleapis.com/v0/b/nitj-companion-24386.appspot.com/o/technical%20societies%2Fties.png?alt=media&token=160376b4-c2cb-4e83-8991-87f4d3b5eadc"));
        l1.add(new add_society_1("HUMANITIES", "https://firebasestorage.googleapis.com/v0/b/nitj-companion-24386.appspot.com/o/technical%20societies%2Fhumanities.png?alt=media&token=2cb09f1a-267a-4771-920b-90e1bd971b4e"));*/

        l1.add(new add_society_1("GAMING", "https://yt3.ggpht.com/2tDwwVVEuy0ja92htLiC-JZdfqUB1ogR--jjSph3Ybk068mSQkoOhG9YdvYvgDrWyjCIvi9JOubtFIyi=s900-nd-c-c0xffffffff-rj-k-no"));
        l1.add(new add_society_1("CENTRAL EVENTS","http://kikkidu.com/wp-content/uploads/2011/03/central.jpg"));
        l1.add(new add_society_1("NIGHT EVENTS", "http://www.destination360.com/north-america/us/florida/tampa/images/s/nightlife.jpg"));
        l1.add(new add_society_1("WORKSHOP", "https://pbs.twimg.com/profile_images/666235682826952704/rsksZQJA.jpg"));


        Iterator i = l1.iterator();
        while(i.hasNext())
        {
            add_society_1 a = (add_society_1)i.next();
            DatabaseReference fv = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Societies");
            String key = fv.push().getKey();

            HashMap<String, Object> postValues = a.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("s96"+key, postValues);

            fv.updateChildren(childUpdates);

        }



    }

    public void nf2()
    {
        List<add_society_2> li = new ArrayList();
        /*li.add(new add_society_2("Nitovators","A society that focuses on new innovations and ideas.","Mandeep Singh 9041908054"));
        li.add(new add_society_2("OPENGEEST","Techinal society of CSE Department","RAHUL SETHI: 9988771081"));
        li.add(new add_society_2("CODE-IT","Techinal society of IT Department","Gaurav : 7696446433"));
        li.add(new add_society_2("SOECE","Techinal society of ECE Department","Manoj : 9569479829"));
        li.add(new add_society_2("SAE","Techinal society related to AUTOMOBILES","Narendra : 9023483880"));
        li.add(new add_society_2("CHESS","Techinal society of CHEMICAL Department","Anusha : 8558840079"));
        li.add(new add_society_2("SOCCER","Techinal society of CIVIL Department","Shiv Kumar Gupta : 9530530220"));
        li.add(new add_society_2("SOBER","Techinal society of BIO-TECH Department","- Dinesh Kumar Saini : 7696318660"));
        li.add(new add_society_2("SOME","Techinal society of MECHANICAL Department","PAVNEET SINGH : 8437052349"));
        li.add(new add_society_2("SOEE","Techinal society of ELECTRICAL Department","Jagpreet Singh Bajwa : 9915401819"));
        li.add(new add_society_2("SPICE","Techinal society of IC Department","Jatin Singh : 8968698558"));
        li.add(new add_society_2("TEX-STYLES","Techinal society of TEXTILE Department","Vishal : 7696477464"));
        li.add(new add_society_2("TIES","Techinal society of IP Department","Manpreet : 9464995378"));
        li.add(new add_society_2("HUMANITIES","Techinal society of MBA Department","(Will be updated soon)"));*/

        li.add(new add_society_2("GAMING","Temporary Society for Techniti. Will conduct Lan Gaming events in Techniti 16","-"));
        li.add(new add_society_2("CENTRAL EVENTS","Temporary group will consist of cetral events conducted in Techniti 16","-"));
        li.add(new add_society_2("NIGHT EVENTS","Temporary group will consist of night events conducted in Techniti 16","-"));
        li.add(new add_society_2("WORKSHOP","Temporary group will consist of workshops conducted in Techniti 16","-"));

        Iterator i = li.iterator();
        while(i.hasNext())
        {
            add_society_2 a = (add_society_2)i.next();
            DatabaseReference fv = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Society details");
            String key = a.getName();

            HashMap<String, Object> postValues = a.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(key, postValues);

            fv.updateChildren(childUpdates);

        }
    }

    public void nf3()
    {
        List<Events_list> li = new ArrayList<>();

        // DAY 1 EVENTS
        /*li.add(new Events_list("TECHNITI INNOVATION CHALLENGE","CENTRAL EVENTS","With a vision of reinforcing the relationship between innovation and entrepreneurship in India, TechNITi’16 Innovation Challenge invites all the innovators, entrepreneurs and researchers to showcase their transforming ideas, innovations and prototypes for the betterment of India and its economy. Challenge asks for the most ingenious solutions for a smarter, safer, and more sustainable and connected India.\n\nPrizes worth Rs. 70,000\n\nContact Ayushman Mukherjee 9876986967","FULL DAY EVENT","IT Park G Floor,Conf. Room","10 am - 6 pm","Day 1","1",""));
        li.add(new Events_list("CLOUD COMPUTING","WORKSHOP","Workshop on Cloud Computing\nWorkshop Certification: engineer india\nFee: Rs. 1000/-(inclusive of all Taxes) per participant\n\nContact Harshit 919872668419","FULL DAY EVENT","IT Park G Floor, SMNR Hall","10 am - 6 pm","Day 1","1",""));
        li.add(new Events_list("IOT","WORKSHOP","Workshop on Internet Of Things\nWorkshop Certification: Engineer India\nFee: Rs. 1000/-(inclusive of all Taxes) per participant\n\nContact Harshit 919872668419","FULL DAY EVENT","IT Park 1st Floor, SMNR Hall","10 am - 6 pm","Day 1","1",""));
        li.add(new Events_list("PHOTO EXHIBITION AND BADLAAV","Photography Club","Exhibition hosted by Photography Club and Badlaav","FULL DAY EVENT","IT Park G Floor","10 am - 6 pm","Day 1","1",""));
        li.add(new Events_list("NITJ GATE","CENTRAL EVENTS","NITJ GATE will be an offline test\n A GATE proto type test is to be organise for the following branches:\nElectrical, mechanical, civil, ICE, ECE, IPE, CSE, Chemical, Biotechnology, Mining, Textile, IT.\n\nContact Aman- 7087230212","FULL DAY EVENT","LT 403","10 am - 6 pm","Day 1","1",""));
        li.add(new Events_list("POWER DRIFT", "SAE", "POWERDRIFT is going to be one of the most extravagant and thrilling event of techNITi'16.In this event, participants have to make their own remote controlled car( wired or wireless).There will be an arena composed of hurdles and obstacles through which their car has to pass through. Their car should be able to capable of run over various types of surfaces (including sand, wood, gravel etc) and the car which would be able to pass through all hurdles in minimum time will be declared as winner. \n\nPrizes worth Rs 10000 \n\n\n Contact : Narendra - 9023483880", "FULL DAY EVENT", "IT Park Backside", "10 am - 6 pm", "Day 1", "1", ""));
        li.add(new Events_list("LAN GAMING (NFS)","GAMING CLUB","Lan Gaming Competition for the game NEED FOR SPEED","FULL DAY EVENT","Community Centre","10 am - 6 pm","Day 1","1",""));
        li.add(new Events_list("DRONE SHOW","CENTRAL EVENTS","The Objective of this whole competition is to judge the knowledge and skills of the all the participants, on the basis of which the winners of the Quad copter Championship will be declared. Build your Quad copter to travel from source to destination crossing various interruptions, hurdles etc along the path of journey \n\nPrizes worth Rs. 50,000\n\nContact Aviral mishra 8968892877","FULL DAY EVENT","Admin Block Front","10 am - 6 pm","Day 1","1",""));

        li.add(new Events_list("Top Round-1","Nitovators","This event will have a full personality test of a student. It will judge student’s I.Q.,communication skills, soft skills and their psychological behaviour. \n\nPrizes worth Rs 10,000 \n\n\n Contact : Gurjyot Singh 9988356455","2 hrs","LT 401,402","10 am - 12 pm","DAY 1","1",""));
        li.add(new Events_list("BrainHack","OPENGEEST","Have you missed the opportunity to showcase your talent. It's high time you've hidden from others the fact that your brain works faster than others. Its time to tune up your brain.\n\nPrizes worth Rs 15000\n\n\n Contact : Ravi Teja 8699130390","2 hrs","IT Park G floor Lab 1 & 2","10 am - 12 pm","DAY 1","1",""));
        li.add(new Events_list("Survey Hunt","SOCCER","This event will be conducted in three rounds with increasing difficulty in each.\n\nPrizes worth Rs 10000\n\n\n n Contact : Shiv Kumar Gupta 9530530220","2 hrs","Infront of LT","10 am - 12 pm","DAY 1","1",""));
        li.add(new Events_list("Bussiness Quiz","Quest","Buisness quiz is a quiz about brands, products, logos and business personalities. If you think you are good at all these things, this quiz is a heaven for you. So, use your knowledge and logics and win this quiz.\n\nPrizes worth Rs 10000 \n\n\n Contact : Jaskaran 8437600160","3 hrs","LT 202","10 am - 1 pm","DAY 1","1",""));
        li.add(new Events_list("Brand Buzz","HUMANITIES","Questions related to different brands are put infront of participants. .\n\nPrizes worth Rs 10000","3 hrs","LT 201","10 am - 1 pm","DAY 1","1",""));
        li.add(new Events_list("Serial Solver R 1","Rtist","To build a manually controlled robot using serial communication which can perform specific tasks which can respond according to serial commands.\n\nPrizes worth Rs 25000 \n\n\n Contact :  Sai Karan Thota 9293944949","3 hrs","IT Park Front Parking 1","10 am - 1 pm","DAY 1","1",""));
        li.add(new Events_list("Heat Exchanger","CHESS","'Engineering Design', as Sherwood said, 'is the process of applying the various techniques and scientific principles for the purpose of defining a device, a process, or a system in sufficient detail to permit its physical realization'.\nWe, the chemical engineers, thus provide you an opportunity to apply your techniques and scientific principles to design one of the most prominent process equipment, *Heat Exchanger*. Not only it will test your skills to put the theoretical knowledge to practical world, but provide you a chance to win exciting cash prizes.\nSo, be ready to feel the world of real engineers, bring on your design and compete.\n\nPrizes worth 20000/- \n\n\nContact: Anusha 8558840079","3 hrs","Chemical Dept HT LAB","10 am - 1 pm","DAY 1","1",""));
        li.add(new Events_list("MechKriti","SOME","It is a combination of art and engineering that will provide a platform where you can use your imagination and creativity along with the accuracy of a drafter to make a rangoli!!. \n\nPrizes worth Rs 10,000 \n\n\n Contact : PAVNEET SINGH 8437052349","3 hrs","Tennis Court","10 am - 1 pm","DAY 1","1",""));
        li.add(new Events_list("Endurance","TIES","Endurance is an event in which one has to bring his/her working car model and the load carrying strength of the car will be judged in three rounds, to be explained on the spot. \n\nPrizes worth Rs 10000 \n\n\n Contact : Manpreet  9464995378","3 hrs","Library parking","10 am - 1 pm","DAY 1","1",""));
        li.add(new Events_list("Bio - Entrepreneur","SOBER","You have to develop a B-PLAN to start a company.\n B-PLAN should be related to Biotechnology.\n\nPrizes worth Rs 10000 \n\n\n Contact : Dinesh Kumar Saini 7696318660","3 hrs","LT 101","10 am - 1 pm","DAY 1","1",""));
        li.add(new Events_list("Kya Tu Beer Hai","Entrepreneurship Cell","Do you think you have all that it takes to be an entrepreneur?\nBe a part of our exciting activities and a free workshop to know more. \n2 rounds. Both of them shall be disclosed on the spot.\n\nPrizes worth Rs 10000 \n\n\n Contact : Aditya  8696917911","3 hrs","LT 103","10 am - 1 pm","DAY 1","1",""));
        li.add(new Events_list("Walkie - Talkie","SOEE","A free workshop will be provided so that participants will get the starting point to prepare for this much awaited Mega Event organized by the Society of Electrical Engineering.\n\nPrizes worth Rs 15000 \n\n\n Contact : Jagpreet Singh Bajwa 9915401819","3 hrs","D7","10 am - 1 pm","DAY 1","1",""));
        li.add(new Events_list("Knight Freaks Circuit Encounter","SOECE","Is the world of electronics calling out to you?\nIf yes! Then dive in!!\nCome, Play and let the electronics play with you!\nA team event having 3-4 members in each team.\n\nPrizes worth Rs 10000 \n\n\nContact : Manoj 9569479829","4 hrs","LT 102","10 am - 2 pm","DAY 1","1",""));
        li.add(new Events_list("Code Unlocked","CODE-IT","Coding Team event with max 2 members.\n\nPrizes worth Rs 10000 \n\n\n Contact : Gaurav : 7696446433","2 hrs","LT 201","1 pm - 3 pm","DAY 1","1",""));
        li.add(new Events_list("EDIFICE","SOCCER","A bridge is a structure built to span physical obstacles such as a body of water, valley, or road, for the purpose of providing passage over the obstacle. There are many different designs that all serve unique purposes and apply to different situations. Designs of bridges vary depending on the function of the bridge, the nature of the terrain where the bridge is constructed and anchored, the material used to make it, and the funds available to build it.\n\nPrizes worth Rs 15000\n\n\n n Contact : Shiv Kumar Gupta 9530530220","2:30 hrs","IT Park Front Parking 1","1 pm - 3:30 pm","DAY 1","1",""));
        li.add(new Events_list("Suspension Offdrive","SOME","Design and fabricate your own Remote Control (RC) Vehicle with suitable suspension system, that could carry a glass of water and rush past the finish line while travelling on a rugged terrain!!!\n\nPrizes worth Rs 10,000 \n\n\n Contact : GAGANDEEP SINGH 9530884017","5 hrs","IP Dept Parking 1","1 pm - 6 pm","DAY 1","1",""));
        li.add(new Events_list("CINEASTE","MOVIE CLUB","Let your imagination and creativity fly and achieve new heights with this movie-making event. Here is the great opportunity to get rewarded for your passion of movie.\nThere are several round of movie quiz during the event, audience are also invited and can win goodies in quiz round.\n\nPrizes worth Rs 15000 \n\n\n Contact : Yogendra Singh 8699167314","4 hrs","CSH","2 pm - 6 pm","DAY 1","1",""));
        li.add(new Events_list("GENERAL QUIZ","Quest","General quiz will have questions from history, geography, science, movies, sports etc. If you have a knack about general knowledge, then play this quiz and win cash prizes.\n\nPrizes worth Rs 10000 \n\n\n Contact : Jaskaran 8437600160","3 hrs","LT 202","2 pm - 5 pm","DAY 1","1",""));
        li.add(new Events_list("DESIGN O TEE","TEX-STYLES","This event is based upon testing creative and appealing skills of students on an apparel.\n\nPrizes worth Rs 10000 \n\n\n Contact : Vishal - 7696477464","3 hrs","Tennis Court","2 pm - 5 pm","DAY 1","1",""));
        li.add(new Events_list("Make My Plan","Entrepreneurship Cell","Depending upon who chooses to sponsor us, we shall make all the participants download their app and plan a travel for four in a given budget with the data from that app only.\n\nPrizes worth Rs 10000 \n\n\n Contact : Aditya  8696917911","4 hrs","LT 103","2 pm - 6 pm","DAY 1","1",""));
        li.add(new Events_list("TOP R-2","Nitovators","Round 2 of TOP.","4 hrs","LT 401,402","2 pm - 6 pm","DAY 1","1",""));
        li.add(new Events_list("Circuit Encounter","SOECE","Fusion of Science, Consciousness and Imagination…\nA team event having 3-4 members in each team.\n\nPrizes worth Rs 10000 \n\n\nContact : Manoj 9569479829","2 hrs","IT park ,Sec Floor ,SMNR Hall","3 pm - 5 pm","DAY 1","1",""));
        li.add(new Events_list("GAME OF CODES","OPENGEEST","Send the word.. Send the Ravens.. Brace Yourself, Game Of Codes is here. OpenGeest presents Game of Codes, a perfect event for all those people who love to code.\n\nEvent Description: Game of codes will consist of two rounds. First will be a preliminary round which will be held online and second will be an onsite round.\n\nPrizes worth Rs 10000\n\n\n Contact : RAHUL : 9988771081","3 hrs","IT Park G floor Lab 1 & 2","3 pm - 6 pm","DAY 1","1",""));
        li.add(new Events_list("SERIAL SOLVER R-2","Rtist","Round 2 of Serial Solver","2:30 hrs","IT Park Front Parking 1","3:30 pm - 6 pm","DAY 1","1",""));
        li.add(new Events_list("BEST ENGINEER","TIES","Participants will be grouped in team of 4 randomly on the spot, in which they will be given a certain task. Participants have to show their creativity and team working abilities. \n\nPrizes worth Rs 15000 \n\n\n Contact : Manpreet  9464995378","2:30 hrs","LT 404","3:30 pm - 6 pm","DAY 1","1",""));
        li.add(new Events_list("BRAND BUZZ","HUMANITIES","Round 2 of Brand Buzz","2 hrs","LT 201","4 pm - 6 pm","DAY 1","1",""));
        li.add(new Events_list("SCIENCE SOCCERY","CHESS","One man’s *magic* is another man’s engineering. *Supernatural* is a null word. Science Sorcery is such an event which demands you to unleash the science behind the magic. How that blend of two chemical turn the water into colorful liquids? How it is possible to hold the flame in your hand? Have you ever tried to discover the role chemistry plays in all those street magic tricks? Let your knowledge be your wand, and fire the spells to win this tournament of ultimate wizardry.\n\n Prizes worth 10000  Rupees \n\n\nContact: Anusha 8558840079","2 hrs","LT 101","4 pm - 6 pm","DAY 1","1",""));
        li.add(new Events_list("SURVEY HUNT","SOCCER","This event will be conducted in three rounds with increasing difficulty in each.\n\nPrizes worth Rs 10000\n\n\n n Contact : Shiv Kumar Gupta 9530530220","2 hrs","Infront of LT","4 pm - 6 pm","DAY 1","1",""));
        li.add(new Events_list("DJ PRO SHOWS","NIGHT EVENTS","(Will be updated soon)","NIGHT EVENT","Admin Block Front OAT","6 pm - 8 pm","DAY 1","1",""));
        li.add(new Events_list("ROCK NIGHT","NIGHT EVENTS","(Will be updated soon)","NIGHT EVENT","Admin Block Front OAT","8 pm - 11 pm","DAY 1","1",""));
        */
        //Day 2 events
        /*li.add(new Events_list("","","","4 hrs","","10 am - 2 pm","DAY 1","0",""));
        li.add(new Events_list("TECHNITI INNOVATION CHALLENGE","CENTRAL EVENTS","With a vision of reinforcing the relationship between innovation and entrepreneurship in India, TechNITi’16 Innovation Challenge invites all the innovators, entrepreneurs and researchers to showcase their transforming ideas, innovations and prototypes for the betterment of India and its economy. Challenge asks for the most ingenious solutions for a smarter, safer, and more sustainable and connected India.\n\nPrizes worth Rs. 70,000\n\nContact Ayushman Mukherjee 9876986967","FULL DAY EVENT","IT park ,Sec Floor ,SMNR Hall","10 am - 6 pm","DAY 2","1",""));
        li.add(new Events_list("CLOUD COMPUTING","WORKSHOP","Workshop on Cloud Computing\nWorkshop Certification: engineer india\nFee: Rs. 1000/-(inclusive of all Taxes) per participant\n\nContact Harshit 919872668419","FULL DAY EVENT","IT Park G Floor, SMNR Hall","10 am - 6 pm","DAY 2","1",""));
        li.add(new Events_list("IOT","WORKSHOP","Workshop on Internet Of Things\nWorkshop Certification: Engineer India\nFee: Rs. 1000/-(inclusive of all Taxes) per participant\n\nContact Harshit 919872668419","FULL DAY EVENT","IT Park 1st Floor, SMNR Hall","10 am - 6 pm","DAY 2","1",""));
        li.add(new Events_list("PHOTO EXHIBITION AND BADLAAV","Photography Club","Exhibition hosted by Photography Club and Badlaav","FULL DAY EVENT","IT Park G Floor","10 am - 6 pm","DAY 2","1",""));
        li.add(new Events_list("MOCK UNITED NATIONS(MUN)","CENTRAL EVENTS","MUN (Model United Nations) is an extra curricular activity in many schools and universities, which simulates the functioning of the United Nations.\nThe Aims of an MUN conference are to encourage debate and sharing of views between students from different schools and of different ethnicities. \n\n\nContact - Chinar Garg 8528726659","FULL DAY EVENT","IT Park G Floor,Conf. Room","10 am - 6 pm","DAY 2","1",""));
        li.add(new Events_list("AUTOMOBILE & IC ENGINE", "WORKSHOP", "Workshop on Automobile Mechanics & IC Engine Design\n" +"A two days workshop on Automotive Engine Design. All the concepts are explained in detail with the Help of Theory and With Specially Designed Animations which would help students to visualize Things before practically working on it.\n\nFee: Rs. 1000/-(inclusive of all Taxes) per participant \n\n\nContact - Harshit 919872668419", "FULL DAY EVENT", "LT 204", "10 am - 6 pm", "DAY 2", "1", ""));
        li.add(new Events_list("LAN GAMING (COUNTER STRIKE)","GAMING","Lan Gaming Competition for the game COUNTER STRIKE","FULL DAY EVENT","Community Centre","10 am - 6 pm","DAY 2","1",""));
        li.add(new Events_list("ASPEN HYSIS","WORKSHOP","ASPEN Hysis | Workshop on Process Simulation using ASPEN Hysis\n" + "Aspen Hysis (Advanced System for Process Engineering) is based on techniques for solving flow sheets that were employed by process engineers many years ago. It is widely used process simulator in various chemical, petrochemical, petroleum refining, polymer and coal based plants.\n\nFee: Rs. 1000/-(inclusive of all Taxes) per participant \n\n\nContact - Harshit 919872668419","FULL DAY EVENT","LT 203","10 am - 6 pm","DAY 2","1",""));
        li.add(new Events_list("CAR SOCCER","SAE","You might have seen humans playing football, ever wondered what it is like to play the game with bots? Well this is your chance to do so!! Car Soccer is an event organized under SAE that employs remote controlled cars to play the game of soccer. \n\nPrizes worth Rs 10000 \n\n\n Contact : Narendra - 9023483880","FULL DAY EVENT","IT Park Front Parking 2","10 am - 6 pm","DAY 2","1",""));
        li.add(new Events_list("TECHVILLE","CENTRAL EVENTS","An Exhibition where one can portray different ideas to explore and encourage scientific and technological talent and creative thinking , to develop an understanding about the role of science and technology to meet the needs of the society,etc.","FULL DAY EVENT","Central Lawn","10 am - 6 pm","DAY 2","1",""));

        li.add(new Events_list("BRAIN HACK","OPEN GEEST", "Day 2 of Brain Hack","2 hrs","IT Park G floor Lab 1 & 2","10 am - 12 pm","DAY 2","1",""));
        li.add(new Events_list("KNIGHT FREAKS","SOECE","Day 2 of Knight Freaks","2 hrs","Tennis Court","10 am - 12 pm","DAY 2","1",""));
        li.add(new Events_list("MELA QUIZ","QUEST","This topic will have questions from Music, Entertainment, Literature and Arts. Use all your knowledge here and win this quiz. \n\nPrizes worth Rs 10000 \n\n\n Contact : Jaskaran 8437600160","3 hrs","LT 202","10 am - 1 pm","DAY 2","1",""));
        li.add(new Events_list("MANAGEMENT GURU","HUMANITIES","IT will consists of 4 events and will be of two days and a team of two members .Your management skills will be put to test .\n\nPrizes worth Rs 10000","3 hrs","LT 201","10 am - 1 pm","DAY 2","1",""));
        li.add(new Events_list("BLACKWASH R-1","Nitovators","Team event (maximum 3 members per team) where different cases will be put infront of you and you have to solve these using your detective skills.\n\nPrizes worth Rs 10000 \n\n\n Contact : Mandeep Singh 9041908054","3 hrs","Chemical Dept CE07","10 am - 1 pm","DAY 2","1",""));
        li.add(new Events_list("INNOVATOR MODELLING","SOBER","Prepare model that solves diferent real life probems \n\nPrizes worth Rs 20000 \n\n\n Contact : Dinesh Kumar Saini 7696318660","3 hrs","LT 101","10 am - 1 pm","DAY 2","1",""));
        li.add(new Events_list("MIND THE MIND","SOECE","And The Real Fun for finding The Treasure Begins Here…\nA team event having 3-4 members in each team.\n\nPrizes worth Rs 10000\n\n\n Contact : Manoj 9569479829","3 hrs","LT 102","10 am - 1 pm","DAY 2","1",""));
        li.add(new Events_list("DIMENSIONS","TIES","Dimension is an On-Spot soap carving event where you have to carve the given piece of soap as per the 3-D design given to you. \n\nPrizes worth Rs 10000 \n\n\n Contact : Manpreet  9464995378","3 hrs","LT 103","10 am - 1 pm","DAY 2","1",""));
        li.add(new Events_list("NEED FOR  SPEED","SPICE","To build an autonomous robot which can perform the given specific tasks.\n\nPrizes worth Rs 10000 \n\n\n Contact : Jatin Singh : 8968698558","3 hrs","LT 404","10 am - 1 pm","DAY 2","1",""));
        li.add(new Events_list("TOP R-3","Nitovators","Round 3 of TOP","3 hrs","LT 401,402","10 am - 1 pm","DAY 2","1",""));
        li.add(new Events_list("NIGHT'S WATCH","Rtist","To build an autonomous robot which can follow a specific path by avoiding adjacent white walls.\n\nPrizes worth Rs 25000 \n\n\n Contact : Kunal Sachdeva 9888161889","3 hrs","D3 & D4","10 am - 1 pm","DAY 2","1",""));
        li.add(new Events_list("EDIFICE","SOCCER","Day 2 of Edifice","4 hrs","Civil Dept. Library","10 am - 2 pm","DAY 2","1",""));
        li.add(new Events_list("ROBO WAR","SOME","It will be a savage, Clash of Metals with the alloys grinding red hot. It is a game of style, control, damage and aggression with the robot pits against each other in a deadly combat. It is time to concentrate on the slashing of the bots. Get ready to feel the chills and shivers down your spine and become a part of Robowars.................\n\nPrizes worth Rs 1,30,000 \n\n\n Contact : NAVDEEP SINGH 9779272674","4 hrs","IP Dept Parking 1","10 am - 2 pm","DAY 2","1",""));
        li.add(new Events_list("B-PLAN"," Entrepreneurship Cell","Do you have a product that is due to move the next century? Is your idea one in a million? Do you and your team have the potential to be a youth sensation?\nIf the answer to any of these questions is YES, think no more! . \n\n\n Contact : Princi 7696126532","4 hrs","LT 403","10 am - 2 pm","DAY 2","1",""));
        li.add(new Events_list("SOLAR CAR CHALLENGE","CENTRAL EVENTS","To build a solar car using a solar panel which will run only on solar panels. The efficiency of the solar car and the user control over the solar car will be tested.\n\n\nASHOK KUMAR : 9041605696","5 hrs","Admin Block Front","10 am - 3 pm","DAY 2","1",""));
        li.add(new Events_list("WALKIE - TALKIE","SOEE","Day 2 of Walkie Talkie","2 hrs","D-7","11 am - 1 pm","DAY 2","1",""));
        li.add(new Events_list("WEBMASTER","CODE-IT","Website Development contest. \n\nPrizes worth Rs 10000\n\n\n Contact : Gaurav : 7696446433","2 hrs","IT Park Linux & DBMS Lab","12 pm - 2 pm","DAY 2","1",""));
        li.add(new Events_list("GUEST LECTURE","WORKSHOP","(Details to be updated...)","2 hrs","CSH","2 pm - 4 pm","DAY 2","1",""));
        li.add(new Events_list("JUNKYARD-MANIA R-1","Nitovators","The event is inspired from the popular show on the Discovery Channel,\n" + "Junkyard Wars. As the name suggests, the teams participating utilise the junk\n" + "made available to them to achieve a particular objective, which will be\n" + "disclosed on the spot. \n\nPrizes worth Rs 10000 \n\n\n Contact : Abhishek Bhutta 9876250064\n","4 hrs","IT Park Backside","2 pm - 6 pm","DAY 2","1",""));
        li.add(new Events_list("KYA TU BEER HAI R-1","Entrepreneurship Cell","Do you think you have all that it takes to be an entrepreneur?\nBe a part of our exciting activities and a free workshop to know more. \n2 rounds. Both of them shall be disclosed on the spot.\n\nPrizes worth Rs 10000 \n\n\n Contact : Aditya  8696917911","3 hrs","LT 103","3 pm - 6 pm","DAY 2","1",""));
        li.add(new Events_list("NIGHTS WATCH","Rtist","Round 2 of Nights Watch","2:30 hrs","D3 & D4","3:30 pm - 6 pm","DAY 2","1",""));
        li.add(new Events_list("SCIENCE SOCCERY","CHESS","Day 2 of Science Soccery","2 hrs","LT 101","4 pm - 6 pm","DAY 2","1",""));
        li.add(new Events_list("NEED FOR  SPEED","SPICE","Roound 2 of Need for Speed","2 hrs","LT 404","4 pm - 6 pm","DAY 2","1",""));
        li.add(new Events_list("SURVEY HUNT","SOCCER","Day 2 of Survey Hunt","2 hrs","LT 401,402","4 pm - 6 pm","DAY 2","1",""));
        li.add(new Events_list("BRAINHACK","OPENGEEST","Day 2 of BrainHack","2 hrs","IT Park G floor Lab 1 & 2","4 pm - 6 pm","DAY 2","1",""));
        li.add(new Events_list("STUNT SHOW","CENTRAL EVENTS","Bike stunts by PROFESSIONALS will be displayed.","1:30 hrs","Admin Block Front OAT","6 pm - 7:30 pm","DAY 2","1",""));
        li.add(new Events_list("FASHION SHOW","CENTRAL EVENTS","Fashion show in our college .","1:30 hrs","Main Ground Stage","7:30 pm - 9 pm","DAY 2","1",""));
        li.add(new Events_list("DJ NIGHT","CENTRAL EVENTS","Jamke Naacho .","2 hrs","Main Ground Stage","2 pm - 6 pm","DAY 2","1",""));*/


        /*Day 3 Events
        li.add(new Events_list("","","","4 hrs","","10 am - 2 pm","DAY 1","0",""));
        li.add(new Events_list("TECHNITI INNOVATION CHALLENGE","CENTRAL EVENTS","With a vision of reinforcing the relationship between innovation and entrepreneurship in India, TechNITi’16 Innovation Challenge invites all the innovators, entrepreneurs and researchers to showcase their transforming ideas, innovations and prototypes for the betterment of India and its economy. Challenge asks for the most ingenious solutions for a smarter, safer, and more sustainable and connected India.\n\nPrizes worth Rs. 70,000\n\nContact Ayushman Mukherjee 9876986967","FULL DAY EVENT","IT park ,Sec Floor ,SMNR Hall","10 am - 6 pm","Day 3","1",""));
        li.add(new Events_list("PHOTO EXHIBITION AND BADLAAV","Photography Club","Exhibition hosted by Photography Club and Badlaav","FULL DAY EVENT","IT Park G Floor","10 am - 6 pm","Day 3","1",""));
        li.add(new Events_list("MOCK UNITED NATIONS(MUN)","CENTRAL EVENTS","MUN (Model United Nations) is an extra curricular activity in many schools and universities, which simulates the functioning of the United Nations.\nThe Aims of an MUN conference are to encourage debate and sharing of views between students from different schools and of different ethnicities. \n\n\nContact - Chinar Garg 8528726659","FULL DAY EVENT","IT Park G Floor,Conf. Room","10 am - 6 pm","Day 3","1",""));
        li.add(new Events_list("AUTOMOBILE & IC ENGINE", "WORKSHOP", "Workshop on Automobile Mechanics & IC Engine Design\n" +"A two days workshop on Automotive Engine Design. All the concepts are explained in detail with the Help of Theory and With Specially Designed Animations which would help students to visualize Things before practically working on it.\n\nFee: Rs. 1000/-(inclusive of all Taxes) per participant \n\n\nContact - Harshit 919872668419", "FULL DAY EVENT", "LT 204", "10 am - 6 pm", "Day 3", "1", ""));
        li.add(new Events_list("LAN GAMING","GAMING","Lan Gaming Competition (3rd Day)","FULL DAY EVENT","Community Centre","10 am - 6 pm","Day 3","1",""));
        li.add(new Events_list("ASPEN HYSIS","WORKSHOP","ASPEN Hysis | Workshop on Process Simulation using ASPEN Hysis\n" + "Aspen Hysis (Advanced System for Process Engineering) is based on techniques for solving flow sheets that were employed by process engineers many years ago. It is widely used process simulator in various chemical, petrochemical, petroleum refining, polymer and coal based plants.\n\nFee: Rs. 1000/-(inclusive of all Taxes) per participant \n\n\nContact - Harshit 919872668419","FULL DAY EVENT","LT 203","10 am - 6 pm","Day 3","1",""));
        li.add(new Events_list("ROBO WAR","SOME","It will be a savage, Clash of Metals with the alloys grinding red hot. It is a game of style, control, damage and aggression with the robot pits against each other in a deadly combat. It is time to concentrate on the slashing of the bots. Get ready to feel the chills and shivers down your spine and become a part of Robowars.................\n\nPrizes worth Rs 1,30,000 \n\n\n Contact : NAVDEEP SINGH 9779272674","4 hrs","IP Dept Parking 1","10 am - 2 pm","Day 3","1",""));
        li.add(new Events_list("CIVIL EXPO","SOCCER","It is a mega event where you have a chance to showcase your abilities to present Universal ideas & innovative Solutions to various Problems. The Problems should be related to daily life civil aspects like sewage problems, water logging, Dampness, Road Design, Earthquakes , Tsunami etc with help of an exemplary model\n\n\n Contact : Shiv Kumar Gupta 9530530220","FULL DAY EVENT","","10 am - 6 pm","Day 3","1",""));
        li.add(new Events_list("TECHVILLE","CENTRAL EVENTS","An Exhibition where one can portray different ideas to explore and encourage scientific and technological talent and creative thinking , to develop an understanding about the role of science and technology to meet the needs of the society,etc.","FULL DAY EVENT","Central Lawn","10 am - 6 pm","Day 3","1",""));
        li.add(new Events_list("RUBIX CUBE WAR","CENTRAL EVENTS","(Details will be updatd soon...)","FULL DAY EVENT","Admin Block Front OAT","10 am - 6 pm","DAY 3","1",""));

        li.add(new Events_list("MAD ADS","MOVIE CLUB","Have ever advertised any product?? Here is a chance to bag a attractive prise money for your advertisement. Create an advertisement of a product of your own choice, keeping in mind that the advertisement must not endorse vulgarity or obscenity in any manner.\n\nPrizes worth Rs 10000 \n\n\n Contact : Rishu Jain 9501053856","3 hrs","CSH","10 am - 1 pm","DAY 3","1",""));
        li.add(new Events_list("DIMENSIONS","TIES","Day 2 of Dimensions","3 hrs","IT Park G Floor, SMNR Hall","10 am - 1 pm","DAY 3","1",""));
        li.add(new Events_list("CHEMITRIX","CHESS","Do you want to explore the capability of your brain and invent something great at this TechNiti’16? If you want to show case your skills based on physics and chemical laws to design a Goldberg Chain Mechanism then this is the perfect event for you organised by CHESS society. .\n\nPrizes worth 10000/- \n\n\n Contact: Anusha 8558840079","3 hrs","IT Park Front Parking 1","10 am - 1 pm","DAY 3","1",""));
        li.add(new Events_list("DESIGN O WEAVE","TEX-STYLES","This event will test skills of students about their basic insight about weaving. They will showcase their talents by weaving belts and bags from fabric stripes in a two round quest and the students will even be provided a course on weaving before the start so that they can accomplish the job with perfection. \n\nPrizes worth Rs 10000 \n\n\n Contact : Vishal - 7696477464","3 hrs","Tennis Court","10 am - 1 pm","DAY 3","1",""));
        li.add(new Events_list("BIO SCRUIZ","SOBER","Quiz that contains questions related to BioTechnology. \n\nPrizes worth Rs 10000 \n\n\n Contact : Dinesh Kumar Saini 7696318660","3 hrs","LT 101","10 am - 1 pm","DAY 3","1",""));
        li.add(new Events_list("KYA TU BEER HAI","Entrepreneurship Cell","Day 2 of Kya Tu Beer Hai","3 hrs","LT 103","10 am - 1 pm","DAY 3","1",""));
        li.add(new Events_list("MAZE RUNNER","SPICE","The objective of whole event is to judge, how participants manage their knowledge, skills and money to reach the end of the problem statement given to them. You are stuck in a Maze.You have to come out of it using your circuital skills.The maze is having different paths to come out. Each path has different difficulties and barriers. All the barriers need to be crossed using your money and solution to the barrier circuital problem.\n\nPrizes worth Rs 10000 \n\n\n Contact : Jatin Singh : 8968698558","3 hrs","LT 404","10 am - 1 pm","DAY 3","1",""));
        li.add(new Events_list("ROBO DRIVE","Rtist","To build a Line Following robot using arduino and IR sensors which can move on a black track and pass through hurdles which simulate real road conditions.\n\nPrizes worth Rs 15000 \n\n\n Contact : Vijay Sekaran 8968698095","3 hrs","D3 & D4","10 am - 1 pm","DAY 3","1",""));
        li.add(new Events_list("MANAGEMENT GURU","HUMANITIES","Day 2 of Management Guru","4 hrs","LT 201","10 am - 2 pm","DAY 3","1",""));
        li.add(new Events_list("MAC-CAR-ISM","SAE","Do you like non-electric mechanisms? Do you know that you can make cars on these mechanisms? SAE NITJ along with techNITi brings you the same with your own innovations. Design, learn and be practical about your own ideas.\n\nPrizes worth Rs 10000 \n\n\n Contact : Narendra - 9023483880","4 hrs","IT Park Front Parking 2","10 am - 2 pm","DAY 3","1",""));
        li.add(new Events_list("BLACK WASH R-2","Nitovators","Round 2 of Blackwash","4 hrs","Chemical Dept CE07","10 am - 2 pm","DAY 3","1",""));
        li.add(new Events_list("KNIGHT FREAKS CIRCUIT ENCOUNTER","SOECE","Day 3 of Knight Freaks","4 hrs","LT 401,402","10 am - 2 pm","DAY 3","1",""));
        li.add(new Events_list("./EXECUTE","OPENGEEST","It is an individual Event.\n• Requires some knowledge about algorithms.\n• Open for all branches\n• There will be 2 rounds in this event..\n• There will be two online coding rounds that will be conducted on Hackerearth and / or Codechef.\n\nPrizes worth Rs 15000\n\n\n Contact : TUSHAR MALHOTRA 8591081645","4 hrs","IT Park Linux & DBMS Lab","10 am - 2 pm","DAY 3","1",""));
        li.add(new Events_list("B-PLAN","Entrepreneurship Cell","Day 2 of B Plan","5 hrs","LT 403","1 pm - 6 pm","DAY 3","1",""));
        li.add(new Events_list("GUEST LECTURE","WORKSHOP","(Details will be updated soon...)","2 hrs","CSH","2 pm - 4 pm","DAY 3","1",""));
        li.add(new Events_list("JUNKYARD MANIA R-2"," Nitovators","Junkyard Mania Round 2","4 hrs","IT Park Backside","2 pm - 6 pm","DAY 3","1",""));
        li.add(new Events_list("BEST ENGINEER","TIES","Day 3 of Best Engineer","2:30 hrs","LT 103","3:30 pm - 6 pm","DAY 3","1",""));
        li.add(new Events_list("ROBO DRIVE","Rtist","Robo Drive (continued...)","2:30 hrs","D3 & D4","3:30 pm - 6 pm","DAY 3","1",""));
        li.add(new Events_list("SCIENCE SOCCERY","CHESS","Day 3 of Science Soccery","2 hrs","LT 101","4 pm - 6 pm","DAY 3","1",""));
        li.add(new Events_list("MAZE RUNNER","SPICE","Maze Runner(continued...)","2 hrs","LT 404","4 pm - 6 pm","DAY 3","1",""));
        li.add(new Events_list("CODE UNLOCKED","CODE-IT","Coding Team event with max 2 members.\n\nPrizes worth Rs 10000 \n\n\n Contact : Gaurav : 7696446433","2 hrs","IT Park Linux & DBMS Lab","4 pm - 6 pm","DAY 3","1",""));
        li.add(new Events_list("CLOSING CEREMONY","NIGHT EVENTS","Closing Ceremony of Techniti 16","2 hrs","CSH","6 pm - 8 pm","DAY 3","1",""));
        li.add(new Events_list("STAR NITE","CSH","Gajendra Verma's Show.","3 hrs","NIGHT EVENT","8 pm - 11 pm","DAY 3","1",""));
        */


        //li.add(new Events_list("","","","4 hrs","","10 am - 2 pm","DAY 1","1",""));
        li.add(new Events_list("","","","4 hrs","","10 am - 2 pm","DAY 1","0",""));
        li.add(new Events_list("Jasoosi Nigahe","Rajbhasha Samiti","This is a team event with 2-3 members per team. \nYou will be given intriguing situations to solve on\n" + "site.\n\nContact - Arpit : 9872663877","","OAT","6 pm","25-Oct","1",""));
        li.add(new Events_list("Tech Talk By Oracle","-","All 3rd year CS/IT/ECE to be present!\n\nAttendance to be taken there. Mandatory for all the above students .","2:30 hrs","CSH","10:00 AM - 12:30 PM","26th-Oct","1",""));
        li.add(new Events_list("Battle of Oratory (Final Round)","Literary and Debating Club","Do you believe you can advocate one alternative over the other? If yes, you have all the right reasons to compete in LADC's this year's leaf of BATTLE OF ORATORY, that focuses on 'What alternative should India pursue to counter terrorism: military operations or diplomacy?'","","Lawn Tennis Court","6 pm","26-Oct","1",""));




        Iterator i = li.iterator();
        while(i.hasNext())
        {
            Events_list a = (Events_list)i.next();
            DatabaseReference dr = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Events");
            String key = dr.push().getKey();
            a.id = key;

            HashMap<String, Object> postValues = a.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(key, postValues);

            dr.updateChildren(childUpdates);

        }
    }


}
//li.add(new Events_list("","","","4 hrs","","10 am - 2 pm","DAY 1","1",""));