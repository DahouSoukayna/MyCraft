package com.example.hraftiproject.Database;


import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;//
        import android.util.Log;

import com.example.hraftiproject.Model.JobModel;
import com.example.hraftiproject.firestoremodels.Professionnel;
import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.firestore.CollectionReference;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.Query;
        import com.google.firebase.firestore.QueryDocumentSnapshot;
        import com.google.firebase.firestore.QuerySnapshot;

        import java.io.ByteArrayOutputStream;
        import java.util.ArrayList;

public class FirestoreHelper {

    private FirebaseFirestore firestore;
    private CollectionReference professionnelCollection;

    public FirestoreHelper() {
        firestore = FirebaseFirestore.getInstance();
        professionnelCollection = firestore.collection("professionnels");
    }

    public void addNewProfessionnel(String nom, String email, String password, String mt, int numtel, String ville, String description, Bitmap image) {
        // Convertir l'image en tableau de bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageInBytes = byteArrayOutputStream.toByteArray();

        Professionnel professionnel = new Professionnel(nom, email, password, mt, numtel, ville, description, imageInBytes);

        professionnelCollection.add(professionnel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("FirestoreHelper", "Professionnel ajouté avec l'ID : " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e("FirestoreHelper", "Erreur lors de l'ajout du professionnel", e);
                    }
                });
    }

    public void updatePassword(String email, String newPassword) {
        Query query = professionnelCollection.whereEqualTo("email", email);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    document.getReference().update("password", newPassword)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("FirestoreHelper", "Mot de passe mis à jour avec succès");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    Log.e("FirestoreHelper", "Erreur lors de la mise à jour du mot de passe", e);
                                }
                            });
                }
            } else {
                Log.e("FirestoreHelper", "Erreur lors de la recherche du professionnel par email", task.getException());
            }
        });
    }

    public ArrayList<JobModel> readJobs() {
        ArrayList<JobModel> jobModelArrayList = new ArrayList<>();

        professionnelCollection.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Professionnel professionnel = document.toObject(Professionnel.class);
                            jobModelArrayList.add(new JobModel(
                                    professionnel.getMetier(),
                                    professionnel.getNom(),
                                    String.valueOf(professionnel.getNumtel()),
                                    professionnel.getVille(),
                                    professionnel.getDescription(),
                                    professionnel.getImageBitmap()
                            ));
                        }
                    } else {
                        Log.e("FirestoreHelper", "Erreur lors de la lecture des professionnels", task.getException());
                    }
                });

        return jobModelArrayList;
    }



}
