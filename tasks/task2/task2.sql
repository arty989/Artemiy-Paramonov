SELECT post.post_id FROM post
INNER JOIN comment ON post.post_id = comment.post_id
WHERE LEFT(post.title, 1) ~ '^[0-9]' AND LENGTH(post.content) > 20
GROUP BY post.post_id
HAVING COUNT(comment.post_id) = 2
ORDER BY post.post_id ASC