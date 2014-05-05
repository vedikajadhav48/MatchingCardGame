package com.vedika.matchingcardgame;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	//cardArray stores positions of the two cards selected for comparison
	private ArrayList<Integer> cardArray = new ArrayList<Integer>();
	//cardStatus is used to check if the card has already been matched
	private boolean[] cardStatus = new boolean[24];
	//cardsMatched is used to check end of the game
	private int cardsMatched = 0;

	// references to our images
	private blankImageWithFaceImage[] imageIds = {
			new blankImageWithFaceImage(R.drawable.images, R.drawable.club2),
			new blankImageWithFaceImage(R.drawable.images, R.drawable.club8),
			new blankImageWithFaceImage(R.drawable.images, R.drawable.diamond6),
			new blankImageWithFaceImage(R.drawable.images, R.drawable.diamond9),
			new blankImageWithFaceImage(R.drawable.images,
					R.drawable.diamond_king),
			new blankImageWithFaceImage(R.drawable.images,
					R.drawable.heart_king),
			new blankImageWithFaceImage(R.drawable.images,
					R.drawable.heart_jack),
			new blankImageWithFaceImage(R.drawable.images,
					R.drawable.heart_queen),
			new blankImageWithFaceImage(R.drawable.images, R.drawable.heart3),
			new blankImageWithFaceImage(R.drawable.images, R.drawable.spade4),
			new blankImageWithFaceImage(R.drawable.images,
					R.drawable.spade_king),
			new blankImageWithFaceImage(R.drawable.images, R.drawable.spade9),
			new blankImageWithFaceImage(R.drawable.images, R.drawable.club2),
			new blankImageWithFaceImage(R.drawable.images, R.drawable.club8),
			new blankImageWithFaceImage(R.drawable.images, R.drawable.diamond6),
			new blankImageWithFaceImage(R.drawable.images, R.drawable.diamond9),
			new blankImageWithFaceImage(R.drawable.images,
					R.drawable.diamond_king),
			new blankImageWithFaceImage(R.drawable.images,
					R.drawable.heart_king),
			new blankImageWithFaceImage(R.drawable.images,
					R.drawable.heart_jack),
			new blankImageWithFaceImage(R.drawable.images,
					R.drawable.heart_queen),
			new blankImageWithFaceImage(R.drawable.images, R.drawable.heart3),
			new blankImageWithFaceImage(R.drawable.images, R.drawable.spade4),
			new blankImageWithFaceImage(R.drawable.images,
					R.drawable.spade_king),
			new blankImageWithFaceImage(R.drawable.images, R.drawable.spade9) };

	public ImageAdapter(Context c) {
		mContext = c;
		//used to show up random images in gridView
		Random rnd = new Random();
		for (int i = imageIds.length - 1; i >= 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			blankImageWithFaceImage temp = imageIds[index];
			imageIds[index] = imageIds[i];
			imageIds[i] = temp;
		}
	}

	public blankImageWithFaceImage getItem(int position) {
		return imageIds[position];
	}

	public int getCount() {
		return imageIds.length;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(50, 50));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(5, 5, 5, 5);
		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setImageResource(imageIds[position].getBlankImageId());
		return imageView;
	}

	protected class blankImageWithFaceImage extends Object {
		private int blankImageId;
		private int faceImageId;

		blankImageWithFaceImage(int bId, int fId) {
			blankImageId = bId;
			faceImageId = fId;
		}

		public int getBlankImageId() {
			return blankImageId;
		}

		public int getFaceImageId() {
			return faceImageId;
		}

		public void setBlankImageId(int bId) {
			this.blankImageId = bId;
		}

		public void setFaceImageId(int fId) {
			this.faceImageId = fId;
		}
	}

	/**
	 * @param imageIds
	 *            the imageIds to set
	 */
	public void swapImageIds(int position) {
		if (cardStatus[position] == false) {
			Log.i("MYLOG", position + "");
			int bId = imageIds[position].getBlankImageId();
			imageIds[position].setBlankImageId(imageIds[position]
					.getFaceImageId());
			imageIds[position].setFaceImageId(bId);
			cardArray.add(position);

			if (cardArray.size() == 1) {
				return;
			}

			if (cardArray.size() == 2) {
				int firstImageId = imageIds[cardArray.get(0)].getBlankImageId();
			int secondImageId = imageIds[cardArray.get(1)].getBlankImageId();

				if (firstImageId == secondImageId) {
					cardStatus[cardArray.get(0)] = true;
					cardStatus[cardArray.get(1)] = true;
					cardsMatched++;

					cardArray.clear();
					return;
				}
			}

			for (int i = 0; i < cardArray.size(); i++) {
				restoreImageIds(cardArray.get(i));//if the cards dont match flip them back
			}
			cardArray.clear();
		} else {
			MainActivity.flipCount--;
		}
		return;
	}

	public void restoreImageIds(int position) {
		int bId = imageIds[position].getBlankImageId();
		imageIds[position].setBlankImageId(imageIds[position].getFaceImageId());
		imageIds[position].setFaceImageId(bId);
	}

	public int getCardsMatched() {
		return cardsMatched;
	}
}
