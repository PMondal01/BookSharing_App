package com.example.boighor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boighor.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private Context context;
    private List<Upload> uploadList;

    public MyAdapter(Context context, List<Upload> uploadList) {
        this.context = context;
        this.uploadList = uploadList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.booklist,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Upload upload=uploadList.get(i);
        myViewHolder.textView.setText(upload.getBookName());
        myViewHolder.book_price_textview.setText(upload.getPrice());
        myViewHolder.number_textview.setText(upload.getNumber());
        Picasso.with(context)
                .load(upload.getImageUrl())
                .placeholder(R.drawable.demo_book)
                .fit()
                .centerCrop()
                .into(myViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {
        TextView textView;
        TextView book_price_textview;
        TextView number_textview;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.bookname_card);
            book_price_textview=itemView.findViewById(R.id.bookprice_card);
            number_textview=itemView.findViewById(R.id.number_card);
            imageView=itemView.findViewById(R.id.imageview_card);



        }
    }
}
