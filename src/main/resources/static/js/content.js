$(document).ready(function() {

    $("#blogback").click(function() {
        $(location).prop("href","/Blog")
        return true;
    });


    const BlogName = decodeURIComponent((window.location.hash.substring(1)));

    // alert(BlogName);

    // $("#AddLine").on('click','.article_details',function(e) {
    //     const BlogName = $(e.target);
    //     $(location).prop("href", "/BlogDetail?BlogName="+BlogName.text());



    $.ajax({
            url:"/blog/content",
            type:"POST",
            datatype:"JSON",
            data: "BlogName="+BlogName,
            success:function (data) {
                if(data.code==="0"){
                    const time = data.data[0].createtime;
                    $(".title").append(BlogName)
                    $(".time").append(time.replace("T"," "))
                    $(".category").append(data.data[0].category)
                    $(".user").append(data.data[0].user)
                    // $("#content").append(data.data[0].content)
                    const markdown = data.data[0].content;

                    const initOutline = () => {
                        const headingElements = []
                        Array.from(document.getElementById('preview').children).forEach((item) => {
                            if (item.tagName.length === 2 && item.tagName !== 'HR' && item.tagName.indexOf('H') === 0) {
                                headingElements.push(item)
                            }
                        })

                        let toc = []
                        window.addEventListener('scroll', () => {
                            const scrollTop = window.scrollY
                            toc = []
                            headingElements.forEach((item) => {
                                toc.push({
                                    id: item.id,
                                    offsetTop: item.offsetTop,
                                })
                            })

                            const currentElement = document.querySelector('.vditor-outline__item--current')
                            for (let i = 0, iMax = toc.length; i < iMax; i++) {
                                if (scrollTop < toc[i].offsetTop - 30) {
                                    if (currentElement) {
                                        currentElement.classList.remove('vditor-outline__item--current')
                                    }
                                    let index = i > 0 ? i - 1 : 0
                                    if (document.querySelector('div[data-id="' + toc[index].id + '"]')) {
                                        document.querySelector('div[data-id="' + toc[index].id + '"]').classList.add('vditor-outline__item--current')
                                    }

                                    break
                                }
                            }
                        })
                    }
                    Vditor.preview(document.getElementById('preview'),
                        markdown, {
                            speech: {
                                enable: false,
                            },
                            anchor: 1,
                            after() {
                                if (window.innerWidth <= 768) {
                                    return
                                }
                                const outlineElement = document.getElementById('outline')
                                Vditor.outlineRender(document.getElementById('preview'), outlineElement)
                                if (outlineElement.innerText.trim() !== '') {
                                    outlineElement.style.display = 'none'
                                    initOutline()
                                }
                            },
                        });





                    return true;
                }else {
                    layer.msg(data.errormsg);
                    return false;

                }

            }
        })
    // })






})