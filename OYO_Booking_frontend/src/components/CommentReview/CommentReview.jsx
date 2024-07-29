import './CommentReview.scss';
import { useEffect, useState } from 'react';

import { Avatar } from '@mui/material';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import { format } from 'date-fns';
import publicAccomPlaceAPI from '~/services/apis/publicAPI/publicAccomPlaceAPI';
import { t } from 'i18next';

export default function CommentReview({ dataComment }) {
    const showMoreComments = false; 

    const commentsToShow = showMoreComments ? dataComment : dataComment.slice(0, 5);
    return (
        <>
            {dataComment.length === 0 ? (
                <div className="container__emtyComment">
                    <p>{t('common.noReview')}</p>
                </div>
            ) : (
                <div className="paper container-comments">
                    {commentsToShow.map((item, index) => (
                        <div key={index} className="container-comment">
                            <div className="commentator">
                                <Avatar className="commentator-avatar" alt="Cindy Baker" src={item.avatarUserUrl} />
                                <div className="commentator-name">{item.lastName}</div>
                            </div>
                            <div className="comment-info">
                                <div className="heading">
                                    <div>{StarRating(item.rateStar)}</div>

                                    <div className="comment-time">
                                        <AccessTimeIcon />
                                        <p>{format(new Date(item.createdDate), 'dd/MM/yyyy')}</p>
                                    </div>
                                </div>

                                <div className="comment-content"> {item.content}</div>
                                {item.haveImage === true && (
                                    <div className="image-list">
                                        {item.imageReviewUrls.map((image, imageIndex) => (
                                            <img
                                                key={imageIndex}
                                                src={image}
                                                // alt={`Image ${imageIndex}`}
                                                className="comment-image"
                                            />
                                        ))}
                                    </div>
                                )}
                            </div>
                        </div>
                    ))}
                    <div className="thelast">
                        {dataComment.length > 5 && (
                            <button onClick={() => setShowMoreComments(!showMoreComments)} className="show-more-button">
                                {showMoreComments ? 'Thu gọn' : 'Xem thêm'}
                            </button>
                        )}
                    </div>
                </div>
            )}
        </>
    );
}

function StarRating(rating) {
    const stars = Array.from({ length: rating }, (_, index) => (
        <span key={index} className="star-full">
            &#9733;
        </span>
    ));
    const emptyStars = Array.from({ length: 5 - rating }, (_, index) => (
        <span key={index} className="star-empty">
            &#9734;
        </span>
    ));

    return (
        <div className="star-rating">
            {stars}
            {emptyStars}
        </div>
    );
}
