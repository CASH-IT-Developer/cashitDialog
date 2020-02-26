# cashitDialog
Dialog with multi view ( Top , Center , Bottom )

Top Dialog                 |      Center Dialog        |      Bottom Dialog
:-------------------------:|:-------------------------:|:-------------------------:
![alt text](https://raw.githubusercontent.com/CASH-IT-Developer/cashitDialog/master/top.jpeg)  |  ![alt text](https://raw.githubusercontent.com/CASH-IT-Developer/cashitDialog/master/center.jpeg)  |  ![alt text](https://raw.githubusercontent.com/CASH-IT-Developer/cashitDialog/master/bottom.jpeg)


### How To Use
     implementation "com.github.CASH-IT-Developer:cashitDialog:1.0.0"
     ...
     ...
     
     
### `and` 
     
     allprojects {
         repositories {
             google()
             maven { url "https://maven.google.com" }
             ...
             ...
         }
     }


### Implementation in Programatically

### `Setup Top Dialog`     
            FloatingActionButton fab3 = findViewById(R.id.fab3);
            fab3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Dlg.Builder(MainActivity.this)
                            .setTitle("Im Title Top Dialog")
                            .setDlgPosition(Dlg.Position.TOP)
                            .setContentImage(R.mipmap.ic_launcher)
                            .setDescription("I'm description Top Dialog")
                            .addButtonHoriz("Style 1 (Horizontal)",  Dlg.Style.STYLE1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
    
                                }
                            }).addButtonHoriz("Style 2 (Horizontal)",  Dlg.Style.STYLE2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
    
    
                        }
                    }).addButtonVert("Style 3 (Vertical)",  Dlg.Style.STYLE3, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
    
                        }
                    }).addButtonVert("Style 4 (Vertical)",  Dlg.Style.STYLE4, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
    
    
                        }
                    }).show();
                }
            });

### `Setup Center Dialog`

    FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Dlg.Builder(MainActivity.this)
                            .setTitle("Im Title Center Dialog")
                            .setDlgPosition(Dlg.Position.CENTER)
                            .setDescription("I'm description Center Dialog")
                            .addButtonHoriz("Style 1 (Horizontal)",  Dlg.Style.STYLE1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
    
                                }
                            }).addButtonHoriz("Style 2 (Horizontal)",  Dlg.Style.STYLE2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
    
    
                        }
                    }).addButtonVert("Style 3 (Vertical)",  Dlg.Style.STYLE3, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
    
                        }
                    }).addButtonVert("Style 4 (Vertical)",  Dlg.Style.STYLE4, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
    
    
                        }
                    }).show();
                }
            });

### `Setup Bottom Dialog`    
            FloatingActionButton fab2 = findViewById(R.id.fab2);
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Dlg.Builder(MainActivity.this)
                            .setTitle("Im Title Bottom Dialog")
                            .setDlgPosition(Dlg.Position.BOTTOM)
                            .setDescription("I'm description Bottom Dialog")
                            .addButtonHoriz("Style 1 (Horizontal)",  Dlg.Style.STYLE1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
    
                                }
                            }).addButtonHoriz("Style 2 (Horizontal)",  Dlg.Style.STYLE2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
    
    
                        }
                    }).addButtonVert("Style 3 (Vertical)",  Dlg.Style.STYLE3, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
    
                        }
                    }).addButtonVert("Style 4 (Vertical)",  Dlg.Style.STYLE4, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
    
    
                        }
                    }).show();
                }
            });
    

        
        
   ### Code by vickykdv (https://github.com/vickyKDV) 
   
   
   ### License
          
    Copyright 2020 PT. Kreigan Sentral Teknologi
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
          
    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.