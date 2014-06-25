package com.austindroids.recycleaustin.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.austindroids.recycleaustin.R;
import com.austindroids.recycleaustin.RecyclableTrueFalse;


public class RecycleFlowScreenActivity extends ActionBarActivity {

     Button RecyclableButton;
     Button NonRecyclableButton;





   private RecyclableTrueFalse[] boxcardBoardAnswerBank = new RecyclableTrueFalse[]{

           new RecyclableTrueFalse(R.string.cardBoard, true),
           new RecyclableTrueFalse(R.string.boxBoard, true),
           new RecyclableTrueFalse(R.string.shoeBox, true),
           new RecyclableTrueFalse(R.string.pizzaBox, false)

    };



    private RecyclableTrueFalse[] dummyAnswerBank = new RecyclableTrueFalse[]{

            new RecyclableTrueFalse(R.string.dummyTrueItem, true),
            new RecyclableTrueFalse(R.string.dummyFalseItem, false),

    };





    private Button showRecyclableButton(Button bt1, Button bt2){
        bt1.setVisibility(View.VISIBLE);
        bt2.setVisibility(View.INVISIBLE);
        return bt1;

    }



    private Button showNonRecyclableButton(Button bt1, Button bt2){
        bt1.setVisibility(View.VISIBLE);
        bt2.setVisibility(View.INVISIBLE);
        return bt1;

    }

    private Button hideNonRecyclableButton(Button bt){
        bt.setVisibility(View.INVISIBLE);
        return bt;

    }


    private void hideBothRecyclableButtons(Button bt1, Button bt2){
        bt1.setVisibility(View.INVISIBLE);
        bt2.setVisibility(View.INVISIBLE);

    }



    private Button hideRecyclableButton(Button bt){
        bt.setVisibility(View.INVISIBLE);
        return bt;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Spinner mainItemsSpinner, subItemsSpinner;
        final ArrayAdapter<CharSequence> paperAdapter, boxCardBoardAdapter, aluminumMetalsAdapter, glassAdapter, plasticsAdapter, rigidPlasticsAdapter, organicMatterAdapter, dummyItemsAdapter;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_flow_screen);


        RecyclableButton = (Button) findViewById(R.id.RecycableButton);
        NonRecyclableButton = (Button) findViewById(R.id.NonRecycableButton);


        // Set first spinner to items for the major recycling categories like paper, plastics
        mainItemsSpinner = (Spinner)findViewById(R.id.spinnerOps1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.majorOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainItemsSpinner.setAdapter(adapter);




        // Set second spinner to sub-items corresponding to their major recycling categories in the first spinner
        subItemsSpinner = (Spinner)findViewById(R.id.spinnerOps2);

        paperAdapter = ArrayAdapter.createFromResource(this, R.array.paperOptions, android.R.layout.simple_spinner_item);
        paperAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        boxCardBoardAdapter = ArrayAdapter.createFromResource(this, R.array.boxCardBoardOptions, android.R.layout.simple_spinner_item);
        boxCardBoardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        aluminumMetalsAdapter = ArrayAdapter.createFromResource(this, R.array.alumninumMetalsOptions, android.R.layout.simple_spinner_item);
        aluminumMetalsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        glassAdapter = ArrayAdapter.createFromResource(this, R.array.glassOptions, android.R.layout.simple_spinner_item);
        glassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        plasticsAdapter = ArrayAdapter.createFromResource(this, R.array.plasticsOptions, android.R.layout.simple_spinner_item);
        plasticsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        rigidPlasticsAdapter = ArrayAdapter.createFromResource(this, R.array.rigidPlasticsOptions, android.R.layout.simple_spinner_item);
        rigidPlasticsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        organicMatterAdapter= ArrayAdapter.createFromResource(this, R.array.organicMatterOptions, android.R.layout.simple_spinner_item);
        organicMatterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dummyItemsAdapter= ArrayAdapter.createFromResource(this, R.array.dummyItemsOptions, android.R.layout.simple_spinner_item);
        organicMatterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);





        mainItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){

                /* Sets & resets the  sub-item spinner whenever another main category item is different from the
                 initial selection. */
                subItemsSpinner.setVisibility(View.INVISIBLE);

                /* On first spinner selection, call setSpinner(int) to determine which sub-item options to show in
                in the second spinner */
                setSpinners(pos);


            }



            /*The setSpinners(int) method determines which sub-items to show in the 2nd spinner by determining
            which main category was selected in the first spinner.  The method also determines if the
            the sub-item is recyclable or not and returns the appropriate information in a non-clickable
            button.

            Future enhancement-- return an ImageView image instead of a non-clickable button.
             */
            public void setSpinners(int spinnerSelection){

                switch (spinnerSelection){
                    case 0: // Paper

                        subItemsSpinner.setVisibility(View.VISIBLE);
                       // subItemsSpinner.setAdapter(paperAdapter);

                        subItemsSpinner.setAdapter(dummyItemsAdapter);
                       // showNonRecyclableButton(NonRecyclableButton, RecyclableButton);
                       // hideRecyclableButton(RecyclableButton);

                      //  hideBothRecyclableButtons(RecyclableButton, NonRecyclableButton);

                        subItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){

                                int currentIndex;

                                currentIndex = pos;
                                if (dummyAnswerBank[currentIndex].isTrueQuestion()) {
                                    showRecyclableButton(RecyclableButton, NonRecyclableButton);

                                }
                                else {
                                    showNonRecyclableButton(NonRecyclableButton, RecyclableButton);

                                }



                            }


                            public void onNothingSelected(AdapterView<?> parent){
                                // nothing here
                            }


                        });


                        break;


                    case 1: // Boxboards & Cardboards

                        subItemsSpinner.setVisibility(View.VISIBLE);
                        subItemsSpinner.setAdapter(boxCardBoardAdapter);


                        subItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){

                                int currentIndex;

                                currentIndex = pos;
                                if (boxcardBoardAnswerBank[currentIndex].isTrueQuestion()) {
                                    showRecyclableButton(RecyclableButton, NonRecyclableButton);

                                }
                                else {
                                   showNonRecyclableButton(NonRecyclableButton, RecyclableButton);

                                }
                            }


                            public void onNothingSelected(AdapterView<?> parent){
                                // nothing here
                            }


                        });

                        break;

                    case 2: // Aluminum & Metals

                        subItemsSpinner.setVisibility(View.VISIBLE);
                        //subItemsSpinner.setAdapter(aluminumMetalsAdapter);

                        subItemsSpinner.setAdapter(dummyItemsAdapter);
                        subItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                                //if ()
                                int currentIndex;

                                currentIndex = pos;
                                if (dummyAnswerBank[currentIndex].isTrueQuestion()) {
                                    showRecyclableButton(RecyclableButton, NonRecyclableButton);

                                }
                                else {
                                    showNonRecyclableButton(NonRecyclableButton, RecyclableButton);

                                }



                            }


                            public void onNothingSelected(AdapterView<?> parent){
                                // nothing here
                            }


                        });


                        break;

                    case 3: // Glass

                        subItemsSpinner.setVisibility(View.VISIBLE);
                        //subItemsSpinner.setAdapter(glassAdapter);

                        subItemsSpinner.setAdapter(dummyItemsAdapter);
                        subItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                                //if ()
                                int currentIndex;

                                currentIndex = pos;
                                if (dummyAnswerBank[currentIndex].isTrueQuestion()) {
                                    showRecyclableButton(RecyclableButton, NonRecyclableButton);

                                }
                                else {
                                    showNonRecyclableButton(NonRecyclableButton, RecyclableButton);

                                }



                            }


                            public void onNothingSelected(AdapterView<?> parent){
                                // nothing here
                            }


                        });

                        break;

                    case 4: // Plastics

                        subItemsSpinner.setVisibility(View.VISIBLE);
                        //subItemsSpinner.setAdapter(plasticsAdapter);

                        subItemsSpinner.setAdapter(dummyItemsAdapter);
                        subItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                                //if ()
                                int currentIndex;

                                currentIndex = pos;
                                if (dummyAnswerBank[currentIndex].isTrueQuestion()) {
                                    showRecyclableButton(RecyclableButton, NonRecyclableButton);

                                }
                                else {
                                    showNonRecyclableButton(NonRecyclableButton, RecyclableButton);

                                }



                            }


                            public void onNothingSelected(AdapterView<?> parent){
                                // nothing here
                            }


                        });

                        break;

                    case 5: // Rigid Plastics

                        subItemsSpinner.setVisibility(View.VISIBLE);
                        //subItemsSpinner.setAdapter(rigidPlasticsAdapter);

                        subItemsSpinner.setAdapter(dummyItemsAdapter);
                        subItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                                //if ()
                                int currentIndex;

                                currentIndex = pos;
                                if (dummyAnswerBank[currentIndex].isTrueQuestion()) {
                                    showRecyclableButton(RecyclableButton, NonRecyclableButton);

                                }
                                else {
                                    showNonRecyclableButton(NonRecyclableButton, RecyclableButton);

                                }



                            }


                            public void onNothingSelected(AdapterView<?> parent){
                                // nothing here
                            }


                        });

                        break;

                    case 6: // Organic Matter
                        subItemsSpinner.setVisibility(View.VISIBLE);
                        //subItemsSpinner.setAdapter(organicMatterAdapter);

                        subItemsSpinner.setAdapter(dummyItemsAdapter);
                        subItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                                //if ()
                                int currentIndex;

                                currentIndex = pos;
                                if (dummyAnswerBank[currentIndex].isTrueQuestion()) {
                                    showRecyclableButton(RecyclableButton, NonRecyclableButton);

                                }
                                else {
                                    showNonRecyclableButton(NonRecyclableButton, RecyclableButton);

                                }



                            }


                            public void onNothingSelected(AdapterView<?> parent){
                                // nothing here
                            }


                        });

                        break;

                    default: hideBothRecyclableButtons(RecyclableButton, NonRecyclableButton);



                };







            }



            public void onNothingSelected(AdapterView<?> parent){
                // nothing here
            }

        });






    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recyclable_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
