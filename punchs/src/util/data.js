import config from "../config"
export const get_recommend_post = () => {
    fetch(config.post_resource)
        .then(data => {
        return data
    }).catch(err => {
        console.log("read post file error", err)
    })
}