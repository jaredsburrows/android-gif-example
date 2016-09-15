package burrows.apps.giphy.example.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import burrows.apps.giphy.example.App;
import burrows.apps.giphy.example.R;
import burrows.apps.giphy.example.rx.event.PreviewImageEvent;
import burrows.apps.giphy.example.ui.adapter.model.GiphyImageInfo;
import burrows.apps.giphy.example.ui.fragment.MainFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifDrawableBuilder;
import pl.droidsonroids.gif.GifImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView adapter for handling Gif Images in a Grid format.
 *
 * @author <a href="mailto:jaredsburrows@gmail.com">Jared Burrows</a>
 */
public final class GiphyAdapter extends RecyclerView.Adapter<GiphyAdapter.GiphyAdapterViewHolder> {
  static final String TAG = MainFragment.class.getSimpleName();
  private static final int GIF_IMAGE_HEIGHT_PIXELS = 128;
  private static final int GIF_IMAGE_WIDTH_PIXELS = GIF_IMAGE_HEIGHT_PIXELS;
  private List<GiphyImageInfo> data = new ArrayList<>();

  @Override public GiphyAdapterViewHolder onCreateViewHolder(final ViewGroup parent, final int position) {
    return new GiphyAdapterViewHolder(LayoutInflater.from(parent.getContext())
                                                    .inflate(R.layout.recyclerview_list_item, parent, false));
  }

  @Override public void onBindViewHolder(final GiphyAdapterViewHolder holder, final int position) {
    final Context context = holder.gifImageView.getContext();
    final GiphyImageInfo model = getItem(position);

    Glide.with(context)
         .load(model.getUrl())
         .asGif()
         .toBytes()
         .thumbnail(0.1f)
         .override(GIF_IMAGE_WIDTH_PIXELS, GIF_IMAGE_HEIGHT_PIXELS)
         .diskCacheStrategy(DiskCacheStrategy.ALL)
         .error(R.mipmap.ic_launcher)
         .into(new SimpleTarget<byte[]>() {
           @Override public void onResourceReady(final byte[] resource,
                                                 final GlideAnimation<? super byte[]> glideAnimation) {
             // Load gif
             final GifDrawable gifDrawable;
             try {
               gifDrawable = new GifDrawableBuilder().from(resource).build();
               holder.gifImageView.setImageDrawable(gifDrawable);
             } catch (final IOException e) {
               holder.gifImageView.setImageResource(R.mipmap.ic_launcher);
             }
             holder.gifImageView.setVisibility(View.VISIBLE);

             // Turn off progressbar
             holder.progressBar.setVisibility(View.INVISIBLE);
             if (Log.isLoggable(TAG, Log.INFO)) {
               Log.i(TAG, "finished loading\t" + model);
             }
           }
         });


//        Glide.with(context)
//                .load(model.getUrl())
//                .asGif()
//                .thumbnail(0.1f)
//                .crossFade()
//                .listener(new RequestListener<String, GifDrawable>() {
//                    @Override
//                    public boolean onException(final Exception e, final String model, final Target<GifDrawable> target,
//                                               final boolean isFirstResource) {
//                        // Update views
//                        holder.progressBar.setVisibility(View.INVISIBLE);
//
//                        holder.gifImageView.setImageResource(R.mipmap.ic_launcher);
//                        holder.gifImageView.setVisibility(View.VISIBLE);
//
//                        if (Log.isLoggable(TAG, Log.ERROR)) {
//                            Log.e(TAG, "onException", e);
//                        }
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(final GifDrawable resource, final String model,
//                                                   final Target<GifDrawable> target, final boolean isFromMemoryCache,
//                                                   final boolean isFirstResource) {
//                        // Update views
//                        holder.gifImageView.startAnimation();
//                        holder.gifImageView.setVisibility(View.VISIBLE);
//
//                        holder.progressBar.setVisibility(View.INVISIBLE);
//                        if (Log.isLoggable(TAG, Log.INFO)) {
//                            Log.i(TAG, "finished loading\t" + model);
//                        }
//                        return false;
//                    }
//                })
//                .override(GIF_IMAGE_WIDTH_PIXELS, GIF_IMAGE_HEIGHT_PIXELS)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .error(R.mipmap.ic_launcher)
//                .into(holder.gifImageView);

    holder.itemView.setOnClickListener(view -> App.get(context).getBus().send(new PreviewImageEvent(model)));
  }

  @Override public void onViewRecycled(final GiphyAdapterViewHolder holder) {
    super.onViewRecycled(holder);

    Glide.clear(holder.gifImageView);
    holder.gifImageView.setImageDrawable(null);
  }

  /**
   * @author <a href="mailto:jaredsburrows@gmail.com">Jared Burrows</a>
   */
  class GiphyAdapterViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.gif_progress) ProgressBar progressBar;
    @BindView(R.id.gif_image) GifImageView gifImageView;

    GiphyAdapterViewHolder(final View view) {
      super(view);

      ButterKnife.bind(this, view);
    }
  }

  /**
   * Returns the number of elements in the data.
   *
   * @return the number of elements in the data.
   */
  @Override public int getItemCount() {
    return data.size();
  }

  /**
   * Returns the instance of the data.
   *
   * @return instance of the data.
   */
  public List<GiphyImageInfo> getList() {
    return data;
  }

  /**
   * Returns the element at the specified location in the data.
   *
   * @param location the index of the element to return.
   * @return the element at the specified location.
   * @throws IndexOutOfBoundsException if {@code location < 0 || location >= size()}
   */
  public GiphyImageInfo getItem(final int location) {
    return data.get(location);
  }

  /**
   * Searches the data for the specified object and returns the index of the
   * first occurrence.
   *
   * @param object the object to search for.
   * @return the index of the first occurrence of the object or -1 if the
   * object was not found.
   */
  public int getLocation(final GiphyImageInfo object) {
    return data.indexOf(object);
  }

  /**
   * Clear the entire adapter using {@link android.support.v7.widget.RecyclerView.Adapter#notifyItemRangeRemoved}.
   */
  public void clear() {
    int size = data.size();
    if (size > 0) {
      for (int i = 0; i < size; i++) {
        data.remove(i);
      }
      notifyItemRangeRemoved(0, size);
    }
  }

  /**
   * Adds the specified object at the end of the data.
   *
   * @param object the object to add.
   * @return always true.
   * @throws UnsupportedOperationException if adding to the data is not supported.
   * @throws ClassCastException            if the class of the object is inappropriate for this
   *                                       data.
   * @throws IllegalArgumentException      if the object cannot be added to the data.
   */
  public boolean add(final GiphyImageInfo object) {
    final boolean added = data.add(object);
    notifyItemInserted(data.size() + 1);
    return added;
  }

  /**
   * Adds the objects in the specified collection to the end of the data. The
   * objects are added in the order in which they are returned from the
   * collection's iterator.
   *
   * @param collection the collection of objects.
   * @return {@code true} if the data is modified, {@code false} otherwise
   * (i.e. if the passed collection was empty).
   * @throws UnsupportedOperationException if adding to the data is not supported.
   * @throws ClassCastException            if the class of an object is inappropriate for this
   *                                       data.
   * @throws IllegalArgumentException      if an object cannot be added to the data.
   */
  public boolean addAll(final List<GiphyImageInfo> collection) {
    final boolean added = data.addAll(collection);
    notifyItemRangeInserted(0, data.size() + 1);
    return added;
  }

  /**
   * Inserts the specified object into the data at the specified location.
   * The object is inserted before the current element at the specified
   * location. If the location is equal to the size of the data, the object
   * is added at the end. If the location is smaller than the size of the
   * data, then all elements beyond the specified location are moved by one
   * location towards the end of the data.
   *
   * @param location the index at which to insert.
   * @param object   the object to add.
   * @throws UnsupportedOperationException if adding to the data is not supported.
   * @throws ClassCastException            if the class of the object is inappropriate for this
   *                                       data.
   * @throws IllegalArgumentException      if the object cannot be added to the data.
   * @throws IndexOutOfBoundsException     if {@code location < 0 || location > size()}
   */
  public void add(final int location, final GiphyImageInfo object) {
    data.add(location, object);
    notifyItemInserted(location);
  }

  /**
   * Removes the first occurrence of the specified object from the data.
   *
   * @param object the object to remove.
   * @return true if the data was modified by this operation, false
   * otherwise.
   * @throws UnsupportedOperationException if removing from the data is not supported.
   */
  public boolean remove(final int location, final GiphyImageInfo object) {
    final boolean removed = data.remove(object);
    notifyItemRangeRemoved(location, data.size());
    return removed;
  }

  /**
   * Removes the first occurrence of the specified object from the data.
   *
   * @param object the object to remove.
   * @return true if the data was modified by this operation, false
   * otherwise.
   * @throws UnsupportedOperationException if removing from the data is not supported.
   */
  public boolean remove(final GiphyImageInfo object) {
    final int location = getLocation(object);
    final boolean removed = data.remove(object);
    notifyItemRemoved(location);
    return removed;
  }

  /**
   * Removes the object at the specified location from the data.
   *
   * @param location the index of the object to remove.
   * @return the removed object.
   * @throws UnsupportedOperationException if removing from the data is not supported.
   * @throws IndexOutOfBoundsException     if {@code location < 0 || location >= size()}
   */
  public GiphyImageInfo remove(final int location) {
    final GiphyImageInfo removedObject = data.remove(location);
    notifyItemRemoved(location);
    notifyItemRangeChanged(location, data.size());
    return removedObject;
  }
}
